package com.example.gridscircles.domain.order.repository;

import com.example.gridscircles.domain.order.entity.OrderProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("""
        select op
          from OrderProduct op
          join fetch op.product p
          join fetch op.orders o
         where o.id = :orderId
        """)
    List<OrderProduct> findByOrdersIdWithProductAndOrder(
        @Param("orderId") Long orderId
    );

    @Modifying
    @Query("DELETE FROM OrderProduct op WHERE op.orders.id = :orderId")
    void deleteByOrderId(@Param("orderId") Long orderId);
}
