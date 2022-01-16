package com.whosaidmeow.msscbeeroderservice.sm.actions;

import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderEventEnum;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.whosaidmeow.msscbeeroderservice.services.BeerOrderManagerImpl.BEER_ORDER_ID_HEADER;

@Slf4j
@Component
public class ValidationFailedAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        UUID beerOderId = (UUID) context.getMessage().getHeaders().get(BEER_ORDER_ID_HEADER);
        log.debug("Compensating Transaction. Validation failed for: {}", beerOderId);
    }
}
