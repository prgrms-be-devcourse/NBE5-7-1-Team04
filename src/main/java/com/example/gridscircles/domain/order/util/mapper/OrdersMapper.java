package com.example.gridscircles.domain.order.util.mapper;

import com.example.gridscircles.domain.order.dto.CreateOrdersRequest;
import com.example.gridscircles.domain.order.dto.CreateOrdersResponse;
import com.example.gridscircles.domain.order.dto.OrderDetailResponse;
import com.example.gridscircles.domain.order.dto.OrderProductDetailResponse;
import com.example.gridscircles.domain.order.dto.OrdersSearchResponse;
import com.example.gridscircles.domain.order.entity.OrderProduct;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.enums.OrderStatus;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersMapper {

    private OrdersMapper() {
    }

    public static OrderDetailResponse toOrderDetailResponse(
        Orders order,
        List<OrderProduct> products,
        int totalQuantity,
        int totalPrice) {
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
            .editable(order.getOrderStatus() == OrderStatus.PROCESSING)
            .build();
    }

    public static OrderProductDetailResponse toOrderProductDetailResponse(OrderProduct op) {
        return OrderProductDetailResponse.builder()
            .productName(op.getProduct().getName())
            .price(op.getPrice())
            .quantity(op.getQuantity())
            .build();
    }

    public static CreateOrdersResponse toCreateOrdersResponse(Orders order) {
        return CreateOrdersResponse.builder()
            .ordersId(order.getId())
            .build();
    }

    public static Orders fromCreateOrdersRequest(CreateOrdersRequest createOrdersRequest) {
        return Orders.builder()
            .orderStatus(OrderStatus.PROCESSING)
            .email(createOrdersRequest.getEmail())
            .address(createOrdersRequest.getAddress())
            .zipcode(createOrdersRequest.getZipcode())
            .build();
    }

    public static OrdersSearchResponse toOrdersSearchResponse(Orders order,
        List<OrderProduct> orderProducts) {
        List<OrderProductDetailResponse> productInfoList = orderProducts.stream()
            .map(OrdersMapper::toOrderProductDetailResponse)
            .collect(Collectors.toList());

        return OrdersSearchResponse.builder()
            .orderId(order.getId())
            .email(order.getEmail())
            .address(order.getAddress())
            .zipcode(order.getZipcode())
            .totalPrice(order.getTotalPrice())
            .orderStatus(order.getOrderStatus())
            .createdAt(order.getCreatedAt())
            .products(productInfoList)
            .build();
    }
}
