package com.example.gridscircles.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gridscircles.domain.order.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
