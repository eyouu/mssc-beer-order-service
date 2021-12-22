package com.whosaidmeow.msscbeeroderservice.sm.actions;

import com.whosaidmeow.brewery.model.event.AllocateOrderEvent;
import com.whosaidmeow.msscbeeroderservice.config.JmsConfig;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrder;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderEventEnum;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderStatusEnum;
import com.whosaidmeow.msscbeeroderservice.repositories.BeerOrderRepository;
import com.whosaidmeow.msscbeeroderservice.web.mappers.BeerOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.whosaidmeow.msscbeeroderservice.config.JmsConfig.ALLOCATE_ORDER_QUEUE;
import static com.whosaidmeow.msscbeeroderservice.services.BeerOrderManagerImpl.BEER_ORDER_ID_HEADER;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderMapper beerOrderMapper;
    private final BeerOrderRepository beerOrderRepository;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BEER_ORDER_ID_HEADER);
        BeerOrder beerOrder = beerOrderRepository.getById(UUID.fromString(beerOrderId));

        jmsTemplate.convertAndSend(ALLOCATE_ORDER_QUEUE, AllocateOrderEvent.builder()
                .beerOrderDTO(beerOrderMapper.beerOrderToDto(beerOrder))
                .build());

        log.debug("Sent allocation request for order id: {}", beerOrderId);
    }
}
