package com.example.gridscircles.domain.order.entity;

import com.example.gridscircles.domain.order.dto.OrderUpdateRequest;
import com.example.gridscircles.domain.order.enums.OrderStatus;
import com.example.gridscircles.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseEntity {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder
    public Orders(String email, String address, String zipcode, Integer totalPrice,
        OrderStatus orderStatus) {
        this.email = email;
        this.address = address;
        this.zipcode = zipcode;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    public void updateStatusComplete() {
        this.orderStatus = OrderStatus.COMPLETED;
    }

    public void updateOrder(OrderUpdateRequest orderUpdateRequest) {
        this.address = orderUpdateRequest.getAddress();
        this.zipcode = orderUpdateRequest.getZipcode();
    }

    public void cancel() {
        this.orderStatus = OrderStatus.CANCELED;
    }

    public void updateTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
