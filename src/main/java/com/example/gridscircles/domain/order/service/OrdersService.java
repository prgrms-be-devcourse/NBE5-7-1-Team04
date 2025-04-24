package com.example.gridscircles.domain.order.service;

import com.example.gridscircles.domain.order.dto.OrdersSearchResponseDto;
import com.example.gridscircles.domain.order.dto.ProductInfoDto;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.repository.OrdersRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrdersService {

  private final OrdersRepository ordersRepository;

  // 특정 ID에 product와 order정보 조회
  public OrdersSearchResponseDto searchOrderWithItems(Long orderId) {
    Orders orders = ordersRepository.findById(orderId).orElseThrow(()
        -> new NoSuchElementException("해당 주문은 존재 하지 않습니다. ID: " + orderId));

    List<ProductInfoDto> products = orders.getOrderProducts().stream()
        .map(orderProduct -> new ProductInfoDto(
            orderProduct.getProduct().getName(),
            orderProduct.getQuantity(),
            orderProduct.getPrice()
        )).collect(Collectors.toList());

    return new OrdersSearchResponseDto(
        orders.getId(),
        orders.getEmail(),
        orders.getAddress(),
        orders.getZipcode(),
        orders.getTotalPrice(),
        orders.getOrderStatus(),
        orders.getCreatedAt(),
        products
    );

  }

  // 전체 주문 조회
  public Page<OrdersSearchResponseDto> getAllOrders(Pageable pageable) {
    Page<Orders> ordersPage = ordersRepository.findAllByOrderByCreatedAtDesc(pageable);

    return ordersPage.map(order -> new OrdersSearchResponseDto(
        order.getId(),
        order.getEmail(),
        order.getAddress(),
        order.getZipcode(),
        order.getTotalPrice(),
        order.getOrderStatus(),
        order.getCreatedAt(),
        order.getOrderProducts().stream()
            .map(item -> new ProductInfoDto(
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice()
            ))
            .collect(Collectors.toList())
    ));


  }


}


