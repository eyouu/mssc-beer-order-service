package com.whosaidmeow.brewery.model.event;

import com.whosaidmeow.brewery.model.BeerOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateOrderRequestEvent {

    private BeerOrderDTO beerOrderDTO;
}
