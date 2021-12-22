package com.whosaidmeow.msscbeeroderservice.services;

import com.whosaidmeow.msscbeeroderservice.domain.BeerOrder;

import java.util.UUID;

public interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);

    void processValidationResult(UUID orderId, Boolean isValid);
}
