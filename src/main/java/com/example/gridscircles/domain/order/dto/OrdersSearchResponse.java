package com.example.gridscircles.domain.order.dto;

import com.example.gridscircles.domain.order.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// PR시 feat보다 수정 전 코드
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdersSearchResponse {

    private Long orderId;
    private String email;
    private String address;
    private String zipcode;
    private Integer totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private List<OrderProductDetailResponse> products;

    @Builder
    public OrdersSearchResponse(Long orderId, String email, String address, String zipcode,
        int totalPrice,
        OrderStatus orderStatus, LocalDateTime createdAt,
        List<OrderProductDetailResponse> products) {
        this.orderId = orderId;
        this.email = email;
        this.address = address;
        this.zipcode = zipcode;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.products = products;
    }
}
