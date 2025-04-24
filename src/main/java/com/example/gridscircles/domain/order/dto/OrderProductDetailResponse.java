package com.example.gridscircles.domain.order.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProductDetailResponse {

    private String productName;
    private Integer price;
    private Integer quantity;

    @Builder
    public OrderProductDetailResponse(String productName, Integer price, Integer quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
}
