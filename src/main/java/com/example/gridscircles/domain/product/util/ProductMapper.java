package com.example.gridscircles.domain.product.util;

import com.example.gridscircles.domain.product.dto.ProductCreateRequest;
import com.example.gridscircles.domain.product.dto.ProductResponse;
import com.example.gridscircles.domain.product.entity.Product;
import java.io.IOException;
import java.util.Base64;

public class ProductMapper {

    public static Product productCreateRequestToEntity(ProductCreateRequest productCreateRequest){

        int price =
            Integer.parseInt(productCreateRequest.getPrice().replace(",",""));

        try {
            return  Product.builder()
                .name(productCreateRequest.getName())
                .category(productCreateRequest.getCategory())
                .description(productCreateRequest.getDescription())
                .price(price)
                .image(productCreateRequest.getFile().getBytes())
                .contentType(productCreateRequest.getFile().getContentType())
                .del_yn("N")
                .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ProductResponse entityToProductResponse(Product product){
        String priceStr =
            String.format("%,d", product.getPrice());

        return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .price(priceStr)
            .category(product.getCategory())
            .description(product.getDescription())
            .base64EncodeImage(base64Encoding(product.getImage()))
            .contentType(product.getContentType())
            .build();
    }

    private static String base64Encoding(byte [] data) {

        return Base64.getEncoder().encodeToString(data);
    }


}


