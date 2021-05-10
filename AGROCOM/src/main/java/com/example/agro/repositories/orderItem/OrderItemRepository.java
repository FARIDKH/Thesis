package com.example.agro.repositories.orderItem;

import com.example.agro.models.OrderItem;
import com.example.agro.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    Optional<OrderItem> findByProductIdAndOrderId(Long productId,Long orderId);
    Optional<List<OrderItem>> findByCustomerId(Long customerId);

    @Query("SELECT o FROM OrderItem as o where o.product.seller.id = :seller_id and o.status = :status")
    Optional<List<OrderItem>> findAllBySellerIdAndStatus(@Param("seller_id") Long seller_id,
                                                @Param("status") Status status);


    boolean existsByProductIdAndOrderId(Long productId,Long orderId);
}
