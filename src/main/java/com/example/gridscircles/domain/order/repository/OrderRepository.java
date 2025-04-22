package com.example.gridscircles.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gridscircles.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
