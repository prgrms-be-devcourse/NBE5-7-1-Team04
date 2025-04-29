package com.example.gridscircles.domain.product.dto;

import com.example.gridscircles.domain.product.enums.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSearchResponse {

    private Long id;
    private String name;
    private String description;
    private Integer price;
    private String delYN;
    private Category category;

    @Builder
    public ProductSearchResponse(Long id, String name, String description,
        Integer price, String delYN, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.delYN = delYN;
        this.category = category;
    }
}
