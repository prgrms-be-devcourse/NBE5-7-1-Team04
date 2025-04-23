package com.example.gridscircles.domain.order.repository;

import com.example.gridscircles.domain.order.entity.OrderProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("""
        select op
          from OrderProduct op
          join fetch op.product p
         where op.orders.id = :orderId
        """)
    List<OrderProduct> findByOrdersIdWithProduct(@Param("orderId") Long orderId);
}
