package com.whosaidmeow.msscbeeroderservice.web.mappers;

import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderLine;
import com.whosaidmeow.brewery.model.BeerOrderLineDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerOrderLineMapperDecorator.class)
public interface BeerOrderLineMapper {

    BeerOrderLineDTO beerOrderLineToDto(BeerOrderLine line);

    BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDTO dto);
}
