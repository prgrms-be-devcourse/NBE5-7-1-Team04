package com.example.gridscircles.domain.product.util;

import com.example.gridscircles.domain.product.dto.ProductDto;
import com.example.gridscircles.domain.product.dto.ProductForm;
import com.example.gridscircles.domain.product.entity.Product;
import java.io.IOException;

public class ProductMapper {

    public static ProductForm toDto (Product product){

        String priceStr =
            String.format("%,d", product.getPrice());

        return ProductForm.builder()
            .id(product.getId())
            .name(product.getName())
            .category(product.getCategory())
            .description(product.getDescription())
            .price(priceStr)
            .build();
}

public static Product toEntity (ProductForm productForm){

        int price =
            Integer.parseInt(productForm.getPrice().replace(",",""));

        try {
            return  Product.builder()
                .name(productForm.getName())
                .category(productForm.getCategory())
                .description(productForm.getDescription())
                .price(price)
                .image(productForm.getFile().getBytes())
                .contentType(productForm.getFile().getContentType())
                .del_yn("N")
                .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
