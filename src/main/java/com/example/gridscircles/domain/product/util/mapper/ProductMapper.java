package com.example.gridscircles.domain.product.util.mapper;

import com.example.gridscircles.domain.product.dto.ProductCreateRequest;
import com.example.gridscircles.domain.product.dto.ProductListResponse;
import com.example.gridscircles.domain.product.dto.ProductResponse;
import com.example.gridscircles.domain.product.dto.ProductSearchResponse;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.global.exception.ErrorCode;
import com.example.gridscircles.global.exception.ErrorException;
import java.io.IOException;
import java.util.Base64;
import org.springframework.data.domain.Page;

public class ProductMapper {

    public static Product productCreateRequestToEntity(ProductCreateRequest productCreateRequest) {
        int price = Integer.parseInt(productCreateRequest.getPrice().replace(",", ""));

        try {
            return Product.builder()
                .name(productCreateRequest.getName())
                .category(productCreateRequest.getCategory())
                .description(productCreateRequest.getDescription())
                .price(price)
                .image(productCreateRequest.getFile().getBytes())
                .contentType(productCreateRequest.getFile().getContentType())
                .del_yn("N")
                .build();
        } catch (IOException e) {
            throw new ErrorException(ErrorCode.NOT_READABLE_FILE);
        }
    }

    public static ProductResponse entityToProductResponse(Product product) {
        String price = String.format("%,d", product.getPrice());

        return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .price(price)
            .category(product.getCategory())
            .description(product.getDescription())
            .base64EncodeImage(base64Encoding(product.getImage()))
            .contentType(product.getContentType())
            .build();
    }

    private static String base64Encoding(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
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

    public static ProductSearchResponse toProductSearchResponse(Product product) {
        return ProductSearchResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .del_yn(product.getDel_yn())
            .category(product.getCategory())
            .build();
    }
}
