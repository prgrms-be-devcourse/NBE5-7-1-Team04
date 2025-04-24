package com.example.gridscircles.domain.order.mapper;

import com.example.gridscircles.domain.order.dto.CreateOrdersRequest;
import com.example.gridscircles.domain.order.dto.CreateOrdersResponse;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrdersMapper {

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
}
