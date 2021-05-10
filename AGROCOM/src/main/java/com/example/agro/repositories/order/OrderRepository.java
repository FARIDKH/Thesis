package com.example.agro.repositories.order;

import com.example.agro.models.Order;
import com.example.agro.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    Optional<Order> findByCustomerIdAndStatus(Long customerId, Status status);
    


}
