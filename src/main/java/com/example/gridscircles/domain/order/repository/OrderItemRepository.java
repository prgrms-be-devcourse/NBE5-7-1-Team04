package com.example.gridscircles.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gridscircles.domain.order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
