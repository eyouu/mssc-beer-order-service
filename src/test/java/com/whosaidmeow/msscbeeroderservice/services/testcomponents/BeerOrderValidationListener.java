package com.whosaidmeow.msscbeeroderservice.services.testcomponents;

import com.whosaidmeow.brewery.model.event.ValidateOrderRequestEvent;
import com.whosaidmeow.brewery.model.event.ValidateOrderResultEvent;
import com.whosaidmeow.msscbeeroderservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderValidationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(Message message) {
        ValidateOrderRequestEvent validation = (ValidateOrderRequestEvent) message.getPayload();

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, ValidateOrderResultEvent.builder()
                .isValid(true)
                .orderId(validation.getBeerOrderDTO().getId()));
    }
}
