package com.whosaidmeow.msscbeeroderservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.whosaidmeow.brewery.model.BeerPagedList;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrder;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderLine;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderStatusEnum;
import com.whosaidmeow.msscbeeroderservice.domain.Customer;
import com.whosaidmeow.msscbeeroderservice.repositories.BeerOrderRepository;
import com.whosaidmeow.msscbeeroderservice.repositories.CustomerRepository;
import com.whosaidmeow.msscbeeroderservice.services.beer.BeerServiceImpl;
import com.whosaidmeow.msscbeeroderservice.services.beer.model.BeerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Set;
import java.util.UUID;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

@ExtendWith(WireMockExtension.class)
@SpringBootTest
class BeerOrderManagerImplITTest {

    private static final String TEST_CUSTOMER_NAME = "James";
    private static final String TEST_UPC = "12345";
    private static Customer TEST_CUSTOMER;
    private static final UUID TEST_BEER_ID = UUID.randomUUID();

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WireMockServer wireMockServer;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BeerOrderRepository beerOrderRepository;

    @Autowired
    private BeerOrderManager underTest;

    @TestConfiguration
    static class RestTemplateBuilderProvider {

        @Bean(destroyMethod = "stop")
        public WireMockServer wireMockServer() {
            WireMockServer server = with(wireMockConfig().port(8083));
            server.start();
            return server;
        }
    }

    @BeforeEach
    void setUp() {
        TEST_CUSTOMER = customerRepository.save(Customer.builder()
                .customerName(TEST_CUSTOMER_NAME)
                .build());
    }

//    @Test
    public void testNewToAllocated() throws JsonProcessingException {
        BeerDTO beerDTO = BeerDTO.builder().id(TEST_BEER_ID).upc(TEST_UPC).build();
        wireMockServer.stubFor(get(BeerServiceImpl.BEER_UPC_PATH_V1 + "12345")
                .willReturn(okJson(objectMapper.writeValueAsString(beerDTO))));

        BeerOrder beerOrder = createBeerOder();

        BeerOrder result = underTest.newBeerOrder(beerOrder);

        assertNotNull(result);
    }

    private BeerOrder createBeerOder() {
        BeerOrder beerOrder = BeerOrder.builder()
                .customer(TEST_CUSTOMER)
                .build();

        Set<BeerOrderLine> beerOrderLines = Set.of(BeerOrderLine.builder()
                .beerId(TEST_BEER_ID)
                .orderQuantity(1)
                .upc(TEST_UPC)
                .beerOrder(beerOrder)
                .build());

        beerOrder.setBeerOrderLines(beerOrderLines);
        return beerOrder;
    }
}