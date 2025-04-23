package com.example.gridscircles.domain.order.service;

import com.example.gridscircles.domain.order.dto.OrderDetailResponse;
import com.example.gridscircles.domain.order.dto.OrderProductDetailResponse;
import com.example.gridscircles.domain.order.entity.OrderProduct;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.exception.OrderNotFoundException;
import com.example.gridscircles.domain.order.repository.OrderProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderProductRepository orderProductRepository;

    @Transactional(readOnly = true)
    public OrderDetailResponse findOrder(Long orderId) {

        List<OrderProduct> products = orderProductRepository
            .findByOrdersIdWithProductAndOrder(orderId);

        if (products.isEmpty()) {
            throw new OrderNotFoundException("주문 정보를 조회할 수 없습니다.");
        }

        Orders order = products.getFirst().getOrders();

        List<OrderProductDetailResponse> orderProducts = products.stream()
            .map(op -> OrderProductDetailResponse.builder()
                .productName(op.getProduct().getName())
                .price(op.getPrice())
                .quantity(op.getQuantity())
                .build())
            .toList();

        return OrderDetailResponse.builder()
            .orderProducts(orderProducts)
            .totalQuantity(calculateTotalQuantity(orderProducts))
            .totalPrice(calculateTotalPrice(orderProducts))
            .address(order.getAddress())
            .zipcode(order.getZipcode())
            .orderStatus(order.getOrderStatus())
            .build();
    }

    private static int calculateTotalQuantity(List<OrderProductDetailResponse> items) {
        return items.stream()
            .mapToInt(OrderProductDetailResponse::getQuantity)
            .sum();
    }

    private static int calculateTotalPrice(List<OrderProductDetailResponse> items) {
        return items.stream()
            .mapToInt(op -> op.getPrice() * op.getQuantity())
            .sum();
    }
}


