package com.example.gridscircles.domain.product.entity;

import com.example.gridscircles.domain.product.dto.ProductUpdateRequest;
import com.example.gridscircles.domain.product.enums.Category;
import com.example.gridscircles.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.util.Arrays;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Lob
    @Column(columnDefinition = "LONGBLOB", nullable = false)
    private byte[] image;

    @Column(nullable = false)
    private String contentType;

    @Column(name = "del_yn" ,nullable = false)
    private String delYN;

    public void deleted() {
        this.delYN = "Y";
    }

    public void updateProduct(ProductUpdateRequest productUpdateRequest, byte[] image) {
        int formatPrice = Integer.parseInt(productUpdateRequest.getPrice().replace(",", ""));

        updateNameIfChanged(productUpdateRequest.getName());
        updateCategoryIfChanged(productUpdateRequest.getCategory());
        updateDescriptionIfChanged(productUpdateRequest.getDescription());
        updatePriceIfChanged(formatPrice);
        updateImageIfChanged(image);
        updateContentTypeIfChanged(productUpdateRequest.getFile().getContentType());
    }

    private void updateNameIfChanged(String newName) {
        if (!Objects.equals(this.name, newName)) {
            this.name = newName;
        }
    }

    private void updateCategoryIfChanged(Category newCategory) {
        if (this.category != newCategory) {
            this.category = newCategory;
        }
    }

    private void updateDescriptionIfChanged(String newDescription) {
        if (!Objects.equals(this.description, newDescription)) {
            this.description = newDescription;
        }
    }

    private void updatePriceIfChanged(Integer newPrice) {
        if (!Objects.equals(this.price, newPrice)) {
            this.price = newPrice;
        }
    }

    private void updateImageIfChanged(byte[] newImage) {
        if (!Arrays.equals(this.image, newImage)) {
            this.image = newImage;
        }
    }

    private void updateContentTypeIfChanged(String newContentType) {
        if (!Objects.equals(this.contentType, newContentType)) {
            this.contentType = newContentType;
        }
    }

    @Builder
    public Product(String name, Category category, String description, Integer price, byte[] image,
        String contentType, String delYN) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.image = image;
        this.contentType = contentType;
        this.delYN = delYN;
    }
}
