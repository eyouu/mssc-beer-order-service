package com.whosaidmeow.msscbeeroderservice.repositories;


import com.whosaidmeow.msscbeeroderservice.domain.BeerOrder;
import com.whosaidmeow.msscbeeroderservice.domain.BeerOrderStatusEnum;
import com.whosaidmeow.msscbeeroderservice.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.UUID;

public interface BeerOrderRepository  extends JpaRepository<BeerOrder, UUID> {

    Page<BeerOrder> findAllByCustomer(Customer customer, Pageable pageable);

    List<BeerOrder> findAllByOrderStatus(BeerOrderStatusEnum orderStatusEnum);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    BeerOrder findOneById(UUID id);
}
