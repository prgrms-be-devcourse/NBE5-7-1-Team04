package com.example.gridscircles.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gridscircles.domain.order.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
