package com.whosaidmeow.msscbeeroderservice.web.mappers;

import com.whosaidmeow.brewery.model.BeerOrderLineDTO;
import com.whosaidmeow.brewery.model.BeerOrderLineDTO.BeerOrderLineDTOBuilder;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderLine;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderLine.BeerOrderLineBuilder;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-25T14:49:15+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class BeerOrderLineMapperImpl_ implements BeerOrderLineMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public BeerOrderLineDTO beerOrderLineToDto(BeerOrderLine line) {
        if ( line == null ) {
            return null;
        }

        BeerOrderLineDTOBuilder beerOrderLineDTO = BeerOrderLineDTO.builder();

        beerOrderLineDTO.id( line.getId() );
        if ( line.getVersion() != null ) {
            beerOrderLineDTO.version( line.getVersion().intValue() );
        }
        beerOrderLineDTO.createdDate( dateMapper.asOffsetDateTime( line.getCreatedDate() ) );
        beerOrderLineDTO.lastModifiedDate( dateMapper.asOffsetDateTime( line.getLastModifiedDate() ) );
        beerOrderLineDTO.upc( line.getUpc() );
        beerOrderLineDTO.beerId( line.getBeerId() );
        beerOrderLineDTO.orderQuantity( line.getOrderQuantity() );
        beerOrderLineDTO.quantityAllocated( line.getQuantityAllocated() );

        return beerOrderLineDTO.build();
    }

    @Override
    public BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BeerOrderLineBuilder beerOrderLine = BeerOrderLine.builder();

        beerOrderLine.id( dto.getId() );
        if ( dto.getVersion() != null ) {
            beerOrderLine.version( dto.getVersion().longValue() );
        }
        beerOrderLine.createdDate( dateMapper.asTimestamp( dto.getCreatedDate() ) );
        beerOrderLine.lastModifiedDate( dateMapper.asTimestamp( dto.getLastModifiedDate() ) );
        beerOrderLine.beerId( dto.getBeerId() );
        beerOrderLine.upc( dto.getUpc() );
        beerOrderLine.orderQuantity( dto.getOrderQuantity() );
        beerOrderLine.quantityAllocated( dto.getQuantityAllocated() );

        return beerOrderLine.build();
    }
}
