package com.example.gridscircles.domain.order.entity;

import com.example.gridscircles.domain.order.dto.CreateOrdersRequest;
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
import lombok.Setter;

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

    @Setter
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

    public static Orders from(CreateOrdersRequest createOrdersRequest) {
        return Orders.builder()
            .orderStatus(OrderStatus.PROCESSING)
            .email(createOrdersRequest.getEmail())
            .address(createOrdersRequest.getAddress())
            .zipcode(createOrdersRequest.getZipcode())
            .build();
    }

    public void updateStatusComplete() {
        this.orderStatus = OrderStatus.COMPLETED;
    }
}
