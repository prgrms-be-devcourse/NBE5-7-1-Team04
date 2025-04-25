package com.example.gridscircles.domain.product.util.mapper;

import com.example.gridscircles.domain.product.dto.ProductForm;
import com.example.gridscircles.domain.product.dto.ProductListResponse;
import com.example.gridscircles.domain.product.entity.Product;
import java.io.IOException;
import java.util.Base64;
import org.springframework.data.domain.Page;

public class ProductMapper {

    public static ProductForm toDto(Product product) {

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

    public static Product toEntity(ProductForm productForm) {

        int price =
            Integer.parseInt(productForm.getPrice().replace(",", ""));

        try {
            return Product.builder()
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

    public static Page<ProductListResponse> toProductListResponse(Page<Product> productPage) {
        return productPage.map(product ->
            ProductListResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .imageBase64(Base64.getEncoder().encodeToString(product.getImage()))
                .imageType(product.getContentType())
            .build()
        );
    }
}
