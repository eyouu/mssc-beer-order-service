package com.whosaidmeow.msscbeeroderservice.services;

import com.whosaidmeow.brewery.model.BeerOrderDTO;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrder;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderEventEnum;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderStatusEnum;
import com.whosaidmeow.msscbeeroderservice.repositories.BeerOrderRepository;
import com.whosaidmeow.msscbeeroderservice.sm.OrderStateChangeInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeerOrderManagerImpl implements BeerOrderManager {

    public static final String BEER_ORDER_ID_HEADER = "order_id";

    private final BeerOrderRepository beerOrderRepository;
    private final OrderStateChangeInterceptor orderStateChangeInterceptor;
    private final StateMachineFactory<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachineFactory;

    @Override
    public BeerOrder newBeerOrder(BeerOrder beerOrder) {
        beerOrder.setId(null);
        beerOrder.setOrderStatus(BeerOrderStatusEnum.NEW);

        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);
        sendBeerOrderEvent(savedBeerOrder, BeerOrderEventEnum.VALIDATE_ORDER);

        return savedBeerOrder;
    }

    @Override
    public void processValidationResult(UUID orderId, Boolean isValid) {
        BeerOrder beerOrder = beerOrderRepository.getById(orderId);

        if (isValid) {
            sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.VALIDATION_PASSED);

            // Get the latest version of order with valid state
            BeerOrder validatedOrder = beerOrderRepository.getById(orderId);

            sendBeerOrderEvent(validatedOrder, BeerOrderEventEnum.ALLOCATE_ORDER);
        } else {
            sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.VALIDATION_FAILED);
        }
    }

    @Override
    public void beerOrderAllocationPassed(BeerOrderDTO beerOrderDTO) {
        BeerOrder beerOrder = beerOrderRepository.getById(beerOrderDTO.getId());
        sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.ALLOCATION_SUCCESS);
        updateAllocationQuantity(beerOrderDTO);
    }

    @Override
    public void beerOrderAllocationPendingInventory(BeerOrderDTO beerOrderDTO) {
        BeerOrder beerOrder = beerOrderRepository.getById(beerOrderDTO.getId());
        sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.ALLOCATION_NO_INVENTORY);
        updateAllocationQuantity(beerOrderDTO);
    }

    @Override
    public void beerOrderAllocationFailed(BeerOrderDTO beerOrderDTO) {
        BeerOrder beerOrder = beerOrderRepository.getById(beerOrderDTO.getId());

        sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.ALLOCATION_FAILED);
    }

    @Override
    public void beerOrderPickedUp(UUID id) {
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(id);

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            //do process
            sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.BEER_ORDER_PICKED_UP);
        }, () -> log.error("Order Not Found. Id: " + id));
    }

    @Override
    public void cancelOrder(UUID id) {
        beerOrderRepository.findById(id).ifPresentOrElse(beerOrder -> {
            sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.CANCEL_ORDER);
        }, () -> log.error("Order Not Found. Id: " + id));
    }

    private void updateAllocationQuantity(BeerOrderDTO beerOrderDTO) {
        BeerOrder allocatedOrder = beerOrderRepository.getById(beerOrderDTO.getId());

        allocatedOrder.getBeerOrderLines().forEach(orderLine -> {
            beerOrderDTO.getBeerOrderLines().forEach(orderLineDTO -> {
                if (orderLine.getId().equals(orderLineDTO.getId())) {
                    orderLine.setQuantityAllocated(orderLineDTO.getQuantityAllocated());
                }
            });
        });

        beerOrderRepository.saveAndFlush(allocatedOrder);
    }

    private void sendBeerOrderEvent(BeerOrder beerOrder, BeerOrderEventEnum event) {
        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> sm = build(beerOrder);
        Message<BeerOrderEventEnum> message = MessageBuilder
                .withPayload(event)
                .setHeader(BEER_ORDER_ID_HEADER, beerOrder.getId())
                .build();
        sm.sendEvent(message);
    }

    private StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> build(BeerOrder beerOrder) {
        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> sm = stateMachineFactory.getStateMachine(beerOrder.getId());
        sm.stop();

        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.resetStateMachine(new DefaultStateMachineContext<>(beerOrder.getOrderStatus(), null, null, null));
                    sma.addStateMachineInterceptor(orderStateChangeInterceptor);
                });

        sm.start();

        return sm;
    }
}
