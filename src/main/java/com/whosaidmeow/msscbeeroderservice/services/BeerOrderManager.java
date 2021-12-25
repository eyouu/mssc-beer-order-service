package com.whosaidmeow.msscbeeroderservice.services;

import com.whosaidmeow.brewery.model.BeerOrderDTO;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrder;

import java.util.UUID;

public interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);

    void processValidationResult(UUID orderId, Boolean isValid);

    void beerOrderAllocationPassed(BeerOrderDTO beerOrderDTO);

    void beerOrderAllocationPendingInventory(BeerOrderDTO beerOrderDTO);

    void beerOrderAllocationFailed(BeerOrderDTO beerOrderDTO);

    void beerOrderPickedUp(UUID id);

    void cancelOrder(UUID id);
}
