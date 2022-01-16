package com.whosaidmeow.msscbeeroderservice.web.mappers;

import com.whosaidmeow.brewery.model.BeerOrderDTO;
import com.whosaidmeow.brewery.model.BeerOrderDTO.BeerOrderDTOBuilder;
import com.whosaidmeow.brewery.model.BeerOrderLineDTO;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrder;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrder.BeerOrderBuilder;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderLine;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderStatusEnum;
import com.whosaidmeow.msscbeeroderservice.domain.Customer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-25T14:49:15+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class BeerOrderMapperImpl implements BeerOrderMapper {

    @Autowired
    private DateMapper dateMapper;
    @Autowired
    private BeerOrderLineMapper beerOrderLineMapper;

    @Override
    public BeerOrderDTO beerOrderToDto(BeerOrder beerOrder) {
        if ( beerOrder == null ) {
            return null;
        }

        BeerOrderDTOBuilder beerOrderDTO = BeerOrderDTO.builder();

        beerOrderDTO.customerId( beerOrderCustomerId( beerOrder ) );
        beerOrderDTO.id( beerOrder.getId() );
        if ( beerOrder.getVersion() != null ) {
            beerOrderDTO.version( beerOrder.getVersion().intValue() );
        }
        beerOrderDTO.createdDate( dateMapper.asOffsetDateTime( beerOrder.getCreatedDate() ) );
        beerOrderDTO.lastModifiedDate( dateMapper.asOffsetDateTime( beerOrder.getLastModifiedDate() ) );
        beerOrderDTO.customerRef( beerOrder.getCustomerRef() );
        beerOrderDTO.beerOrderLines( beerOrderLineSetToBeerOrderLineDTOList( beerOrder.getBeerOrderLines() ) );
        if ( beerOrder.getOrderStatus() != null ) {
            beerOrderDTO.orderStatus( beerOrder.getOrderStatus().name() );
        }
        beerOrderDTO.orderStatusCallbackUrl( beerOrder.getOrderStatusCallbackUrl() );

        return beerOrderDTO.build();
    }

    @Override
    public BeerOrder dtoToBeerOrder(BeerOrderDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BeerOrderBuilder beerOrder = BeerOrder.builder();

        beerOrder.id( dto.getId() );
        if ( dto.getVersion() != null ) {
            beerOrder.version( dto.getVersion().longValue() );
        }
        beerOrder.createdDate( dateMapper.asTimestamp( dto.getCreatedDate() ) );
        beerOrder.lastModifiedDate( dateMapper.asTimestamp( dto.getLastModifiedDate() ) );
        beerOrder.customerRef( dto.getCustomerRef() );
        beerOrder.beerOrderLines( beerOrderLineDTOListToBeerOrderLineSet( dto.getBeerOrderLines() ) );
        if ( dto.getOrderStatus() != null ) {
            beerOrder.orderStatus( Enum.valueOf( BeerOrderStatusEnum.class, dto.getOrderStatus() ) );
        }
        beerOrder.orderStatusCallbackUrl( dto.getOrderStatusCallbackUrl() );

        return beerOrder.build();
    }

    private UUID beerOrderCustomerId(BeerOrder beerOrder) {
        if ( beerOrder == null ) {
            return null;
        }
        Customer customer = beerOrder.getCustomer();
        if ( customer == null ) {
            return null;
        }
        UUID id = customer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected List<BeerOrderLineDTO> beerOrderLineSetToBeerOrderLineDTOList(Set<BeerOrderLine> set) {
        if ( set == null ) {
            return null;
        }

        List<BeerOrderLineDTO> list = new ArrayList<BeerOrderLineDTO>( set.size() );
        for ( BeerOrderLine beerOrderLine : set ) {
            list.add( beerOrderLineMapper.beerOrderLineToDto( beerOrderLine ) );
        }

        return list;
    }

    protected Set<BeerOrderLine> beerOrderLineDTOListToBeerOrderLineSet(List<BeerOrderLineDTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<BeerOrderLine> set = new HashSet<BeerOrderLine>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( BeerOrderLineDTO beerOrderLineDTO : list ) {
            set.add( beerOrderLineMapper.dtoToBeerOrderLine( beerOrderLineDTO ) );
        }

        return set;
    }
}
