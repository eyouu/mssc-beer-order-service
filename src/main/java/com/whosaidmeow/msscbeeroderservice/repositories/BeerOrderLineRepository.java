package com.whosaidmeow.msscbeeroderservice.repositories;

import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderLine;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerOrderLineRepository extends PagingAndSortingRepository<BeerOrderLine, UUID> {
}
