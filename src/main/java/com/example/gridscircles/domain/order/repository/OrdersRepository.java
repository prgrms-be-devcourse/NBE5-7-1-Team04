package com.example.gridscircles.domain.order.repository;

import com.example.gridscircles.domain.order.entity.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByEmailOrderByCreatedAtDesc(String email);

    List<Orders> findByIdOrderByCreatedAtDesc(Long id);
}
