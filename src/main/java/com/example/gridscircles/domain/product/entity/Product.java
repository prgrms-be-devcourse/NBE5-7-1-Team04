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
    @Column(columnDefinition = "LONGBLOB" ,nullable = false)
    private byte[] image;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private String del_yn;

    public void deleted(){
        this.del_yn = "Y";
    }

    public void updateProduct(ProductUpdateRequest productUpdateRequest , byte [] image) {

        int formatPrice =
            Integer.parseInt(productUpdateRequest.getPrice().replace(",",""));

        if(!Objects.equals(this.name, productUpdateRequest.getName())) {
            this.name = productUpdateRequest.getName();
        }
        if(this.category != productUpdateRequest.getCategory() ){
            this.category = productUpdateRequest.getCategory();
        }
        if(!Objects.equals(this.description, productUpdateRequest.getDescription())) {
            this.description = productUpdateRequest.getDescription();
        }
        if (!Objects.equals(this.price, formatPrice)) {
            this.price = formatPrice;
        }

        if (!Arrays.equals(this.image, image)) {
            this.image = image;
        }

        if(!Objects.equals(this.contentType, productUpdateRequest.getFile().getContentType())) {
            this.contentType = productUpdateRequest.getFile().getContentType();
        }
    }

    @Builder
    public Product(String name, Category category, String description, Integer price, byte[] image,
        String contentType, String del_yn) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.image = image;
        this.contentType = contentType;
        this.del_yn = del_yn;
    }
}
