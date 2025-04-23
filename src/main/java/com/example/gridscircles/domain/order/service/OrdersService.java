package com.example.gridscircles.domain.order.service;

import com.example.gridscircles.domain.order.dto.OrderDetailDto;
import com.example.gridscircles.domain.order.dto.OrderProductDetailDto;
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
public class OrdersService {

    private final OrderProductRepository orderProductRepository;

    @Transactional(readOnly = true)
    public OrderDetailDto getOrderDetail(Long orderId) {
        List<OrderProduct> products = orderProductRepository
            .findByOrdersIdWithProductAndOrder(orderId);

        Orders findOrder = products.stream()
            .findFirst()
            .map(OrderProduct::getOrders)
            .orElseThrow(() -> new OrderNotFoundException("주문 정보를 조회할 수 없습니다."));

        List<OrderProductDetailDto> orderProducts = products.stream()
            .map(this::toDetailDto)
            .toList();

        return toOrderDetailDto(findOrder, orderProducts);
    }

    private OrderProductDetailDto toDetailDto(OrderProduct op) {
        return OrderProductDetailDto.builder()
            .productName(op.getProduct().getName())
            .price(op.getPrice())
            .quantity(op.getQuantity())
            .build();
    }

    private OrderDetailDto toOrderDetailDto(Orders order, List<OrderProductDetailDto> items) {
        return OrderDetailDto.builder()
            .orderProducts(items)
            .totalQuantity(calculdateTotalQuantity(items))
            .totalPrice(calculateTotalPrice(items))
            .address(order.getAddress())
            .zipcode(order.getZipcode())
            .orderStatus(order.getOrderStatus())
            .build();
    }

    private int calculdateTotalQuantity(List<OrderProductDetailDto> items) {
        return items.stream()
            .mapToInt(OrderProductDetailDto::getQuantity)
            .sum();
    }

    private int calculateTotalPrice(List<OrderProductDetailDto> items) {
        return items.stream()
            .mapToInt(item -> item.getPrice() * item.getQuantity())
            .sum();
    }
}


