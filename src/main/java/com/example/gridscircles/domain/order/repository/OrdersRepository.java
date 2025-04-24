package com.example.gridscircles.domain.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gridscircles.domain.order.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

  // fingbyALL 기능에 주문한 시간을 내림차순으로 정렬해서 가져오는 기능
  Page<Orders> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
