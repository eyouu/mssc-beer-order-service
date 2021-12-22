package com.whosaidmeow.msscbeeroderservice.services;


import com.whosaidmeow.brewery.model.BeerOrderDTO;
import com.whosaidmeow.brewery.model.BeerOrderPagedList;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BeerOrderService {

    BeerOrderPagedList listOrders(UUID customerId, Pageable pageable);

    BeerOrderDTO placeOrder(UUID customerId, BeerOrderDTO beerOrderDto);

    BeerOrderDTO getOrderById(UUID customerId, UUID orderId);

    void pickupOrder(UUID customerId, UUID orderId);
}
