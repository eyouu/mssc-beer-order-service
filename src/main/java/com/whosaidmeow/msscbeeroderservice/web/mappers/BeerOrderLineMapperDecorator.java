package com.whosaidmeow.msscbeeroderservice.web.mappers;

import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderLine;
import com.whosaidmeow.msscbeeroderservice.services.beer.BeerService;
import com.whosaidmeow.msscbeeroderservice.services.beer.model.BeerDTO;
import com.whosaidmeow.brewery.model.BeerOrderLineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {

    private BeerService beerService;
    private BeerOrderLineMapper beerOrderLineMapper;

    @Autowired
    public void setBeerService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Autowired
    @Qualifier("delegate")
    public void setBeerOrderLineMapper(BeerOrderLineMapper beerOrderLineMapper) {
        this.beerOrderLineMapper = beerOrderLineMapper;
    }

    @Override
    public BeerOrderLineDTO beerOrderLineToDto(BeerOrderLine line) {
        BeerOrderLineDTO beerOrderLineDto = beerOrderLineMapper.beerOrderLineToDto(line);
        Optional<BeerDTO> beerByUpc = beerService.getBeerByUpc(line.getUpc());

        beerByUpc.ifPresent(beerDTO -> {
            beerOrderLineDto.setBeerName(beerDTO.getBeerName());
            beerOrderLineDto.setBeerStyle(beerDTO.getBeerStyle());
            beerOrderLineDto.setPrice(beerDTO.getPrice());
            beerOrderLineDto.setBeerId(beerDTO.getId());
        });

        return beerOrderLineDto;
    }
}
