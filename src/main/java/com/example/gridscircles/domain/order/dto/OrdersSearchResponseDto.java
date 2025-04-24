package com.example.gridscircles.domain.order.dto;

import com.example.gridscircles.domain.order.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrdersSearchResponseDto {

  private Long orderId;
  private String email;
  private String address;
  private String zipcode;
  private Integer totalPrice;
  private OrderStatus orderStatus;
  private LocalDateTime createdAt;
  private List<ProductInfoDto> products;
}
