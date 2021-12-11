package com.whosaidmeow.msscbeeroderservice.services.beer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDTO  {

    private UUID id;
    private String upc;
    private String beerName;
    private Integer version;
    private BigDecimal price;
    private Integer qualityOnHand;
    private String beerStyle;
    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
}
