package com.whosaidmeow.msscbeeroderservice.web.mappers;

import com.whosaidmeow.msscbeeroderservice.domain.BeerOrder;
import com.whosaidmeow.msscbeeroderservice.web.model.BeerOrderDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);
}
