package com.example.gridscircles.domain.product.dto;

import com.example.gridscircles.domain.product.enums.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;

    private final String name;

    private final Category category;

    private final String description;

    private final String price;

    private final String base64EncodeImage;

    private final String contentType;

    @Builder
    public ProductResponse(Long id, String name, Category category, String description,
        String price,
        String base64EncodeImage, String contentType) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.base64EncodeImage = base64EncodeImage;
        this.contentType = contentType;
    }
}
