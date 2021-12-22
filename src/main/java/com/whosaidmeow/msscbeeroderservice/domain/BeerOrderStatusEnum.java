package com.whosaidmeow.msscbeeroderservice.domain;

public enum BeerOrderStatusEnum {
    NEW,
    VALIDATED, VALIDATION_EXCEPTION,
    ALLOCATION, ALLOCATION_EXCEPTION, PENDING_INVENTORY,
    PICKED_UP, DELIVERED, DELIVERY_EXCEPTION
}
