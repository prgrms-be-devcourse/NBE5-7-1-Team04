package com.example.gridscircles.domain.order.util.mapper;

import com.example.gridscircles.domain.order.dto.CreateOrdersRequest.CreateOrdersProductDto;
import com.example.gridscircles.domain.order.entity.OrderProduct;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderProductMapper {

    public static OrderProduct fromCreateOrdersProductDto(
        CreateOrdersProductDto dto,
        Orders orders,
        Product product
    ) {
        return OrderProduct.builder()
            .orders(orders)
            .product(product)
            .quantity(dto.getQuantity())
            .price(product.getPrice())
            .build();
    }
}
