package com.example.gridscircles.domain.product.dto;

import lombok.Builder;
import lombok.Data;

// 테스트용
@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String category;
    private Integer price;
    private String imageType;
    private String imageBase64;

    @Builder
    public ProductResponse(Long id, String name, String category, Integer price, String imageType,
        String imageBase64) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageType = imageType;
        this.imageBase64 = imageBase64;
    }
}
