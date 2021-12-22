package com.whosaidmeow.msscbeeroderservice.web.mappers;

import com.whosaidmeow.msscbeeroderservice.domain.BeerOrder;
import com.whosaidmeow.brewery.model.BeerOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    @Mapping(target = "customerId", source = "customer.id")
    BeerOrderDTO beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDTO dto);
}
