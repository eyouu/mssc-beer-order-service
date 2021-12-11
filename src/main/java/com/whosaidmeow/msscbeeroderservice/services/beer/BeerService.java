package com.whosaidmeow.msscbeeroderservice.services.beer;

import com.whosaidmeow.msscbeeroderservice.services.beer.model.BeerDTO;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    Optional<BeerDTO> getBeerById(UUID id);

    Optional<BeerDTO> getBeerByUpc(String upc);
}
