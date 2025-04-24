package com.example.gridscircles.domain.product.dto;

import com.example.gridscircles.domain.product.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductSearchResponseDto {
  private Long productId;
  private String productName;
  private String description;
  private Integer price;
  private String image;
  private Category category;
}
