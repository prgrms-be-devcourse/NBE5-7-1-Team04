package com.example.gridscircles.domain.order.util.mapper;

import com.example.gridscircles.domain.order.dto.OrderDetailResponse;
import com.example.gridscircles.domain.order.dto.OrderProductDetailResponse;
import com.example.gridscircles.domain.order.entity.OrderProduct;
import com.example.gridscircles.domain.order.entity.Orders;
import java.util.List;

public class OrdersMapper {

    private OrdersMapper() {
    }

    public static OrderDetailResponse toOrderDetailResponse(
        Orders order,
        List<OrderProduct> products,
        int totalQuantity,
        int totalPrice
    ) {
        List<OrderProductDetailResponse> orderProducts = products.stream()
            .map(OrdersMapper::toOrderProductDetailResponse)
            .toList();

        return OrderDetailResponse.builder()
            .id(order.getId())
            .orderProducts(orderProducts)
            .totalQuantity(totalQuantity)
            .totalPrice(totalPrice)
            .address(order.getAddress())
            .zipcode(order.getZipcode())
            .email(order.getEmail())
            .orderStatus(order.getOrderStatus())
            .build();
    }

    private static OrderProductDetailResponse toOrderProductDetailResponse(OrderProduct op) {
        return OrderProductDetailResponse.builder()
            .productName(op.getProduct().getName())
            .price(op.getPrice())
            .quantity(op.getQuantity())
            .build();
    }
}
