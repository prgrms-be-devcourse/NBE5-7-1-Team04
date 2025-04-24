package com.example.gridscircles.domain.order.repository;

import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.enums.OrderStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Page<Orders> findByEmailOrderByCreatedAtDesc(String email, Pageable pageable);

    List<Orders> findByIdAndEmailOrderByCreatedAt(Long id, String email);

    List<Orders> findByOrderStatus(OrderStatus orderStatus);
}
