package com.example.gridscircles.domain.order.dto;

import com.example.gridscircles.domain.order.enums.OrderStatus;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetailResponse {

    private Long id;
    private String email;
    private List<OrderProductDetailResponse> orderProducts;
    private Integer totalQuantity;
    private Integer totalPrice;
    private String address;
    private String zipcode;
    private OrderStatus orderStatus;
    private Boolean editable;

    @Builder
    public OrderDetailResponse(Long id, List<OrderProductDetailResponse> orderProducts,
        Integer totalQuantity, Integer totalPrice, String address, String zipcode,
        OrderStatus orderStatus, String email, boolean editable) {
        this.id = id;
        this.orderProducts = orderProducts;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.address = address;
        this.zipcode = zipcode;
        this.orderStatus = orderStatus;
        this.email = email;
        this.editable = editable;
    }
}
