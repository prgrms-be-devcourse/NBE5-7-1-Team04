package com.example.gridscircles.domain.product.dto;

import com.example.gridscircles.domain.product.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductListResponse {
    private Long productId;
    private String productName;
    private Integer price;
    private String imageBase64;
    private String imageType;
    private Category category;
}
