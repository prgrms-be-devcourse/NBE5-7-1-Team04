package com.example.gridscircles.domain.order.service;

import com.example.gridscircles.domain.order.dto.OrderDetailResponse;
import com.example.gridscircles.domain.order.dto.OrderProductDetailResponse;
import com.example.gridscircles.domain.order.entity.OrderProduct;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.exception.OrderNotFoundException;
import com.example.gridscircles.domain.order.repository.OrderProductRepository;
import com.example.gridscircles.domain.order.repository.OrdersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional(readOnly = true)
    public OrderDetailResponse findOrder(Long orderId) {
        Orders findOrder = getOrder(orderId);

        List<OrderProduct> products = getOrderProducts(orderId);

        List<OrderProductDetailResponse> orderProducts = products.stream()
            .map(op -> OrderProductDetailResponse.builder()
                .productName(op.getProduct().getName())
                .price(op.getPrice())
                .quantity(op.getQuantity())
                .build())
            .toList();

        return OrderDetailResponse.builder()
            .orderProducts(orderProducts)
            .orderStatus(findOrder.getOrderStatus())
            .totalPrice(calculateTotalPrice(orderProducts))
            .totalQuantity(calculateTotalQuantity(orderProducts))
            .address(findOrder.getAddress())
            .zipcode(findOrder.getZipcode())
            .build();
    }

    private List<OrderProduct> getOrderProducts(Long orderId) {
        return orderProductRepository.findByOrdersIdWithProduct(orderId);
    }

    private Orders getOrder(Long orderId) {
        return ordersRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("주문 정보를 조회할 수 없습니다."));
    }

    private static int calculateTotalPrice(List<OrderProductDetailResponse> orderProducts) {
        return orderProducts.stream()
            .mapToInt(op -> op.getPrice() * op.getQuantity())
            .sum();
    }

    private static int calculateTotalQuantity(List<OrderProductDetailResponse> orderProducts) {
        return orderProducts.stream()
            .mapToInt(OrderProductDetailResponse::getQuantity)
            .sum();
    }
}
