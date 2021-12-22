package com.whosaidmeow.msscbeeroderservice.services.listeners;

import com.whosaidmeow.brewery.model.event.AllocateOrderResultEvent;
import com.whosaidmeow.msscbeeroderservice.config.JmsConfig;
import com.whosaidmeow.msscbeeroderservice.services.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.whosaidmeow.msscbeeroderservice.config.JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderAllocationResultListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listen(AllocateOrderResultEvent result) {
        if (!result.getAllocationError() && !result.getPendingInventory()) {
            // allocated normally
            beerOrderManager.beerOrderAllocationPassed(result.getBeerOrderDTO());
        } else if (!result.getAllocationError() && result.getPendingInventory()) {
            // pending inventory
            beerOrderManager.beerOrderAllocationPendingInventory(result.getBeerOrderDTO());
        } else if (result.getAllocationError()){
            // allocation error
            beerOrderManager.beerOrderAllocationFailed(result.getBeerOrderDTO());
        }
    }
}
