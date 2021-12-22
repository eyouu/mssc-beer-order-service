package com.whosaidmeow.msscbeeroderservice.services.listeners;

import com.whosaidmeow.brewery.model.event.ValidateOrderResultEvent;
import com.whosaidmeow.msscbeeroderservice.services.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.whosaidmeow.msscbeeroderservice.config.JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidationResultListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listen(ValidateOrderResultEvent event) {
        UUID orderId = event.getOrderId();

        log.debug("Validation result for order id: {}", orderId);

        beerOrderManager.processValidationResult(orderId, event.getIsValid());
    }
}
