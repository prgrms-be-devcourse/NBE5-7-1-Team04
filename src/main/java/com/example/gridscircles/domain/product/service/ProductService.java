package com.example.gridscircles.domain.product.service;

import static com.example.gridscircles.global.exception.ErrorCode.*;

import com.example.gridscircles.domain.product.dto.ProductCreateRequest;
import com.example.gridscircles.domain.product.dto.ProductListResponse;
import com.example.gridscircles.domain.product.dto.ProductResponse;
import com.example.gridscircles.domain.product.dto.ProductSearchResult;
import com.example.gridscircles.domain.product.dto.ProductUpdateRequest;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import com.example.gridscircles.domain.product.util.mapper.ProductMapper;
import com.example.gridscircles.global.exception.AlertDetailException;
import com.example.gridscircles.global.exception.ErrorCode;
import com.example.gridscircles.global.exception.ErrorException;
import java.io.IOException;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product saveProduct(ProductCreateRequest productCreateRequest) {
        Product product = ProductMapper.productCreateRequestToEntity(productCreateRequest);

        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ErrorException(NOT_FOUND_PRODUCT));

        return ProductMapper.entityToProductResponse(product);
    }

    @Transactional
    public void deleteProductById(Long id) {
        productRepository.findById(id)
            .orElseThrow(() -> new ErrorException(NOT_FOUND_PRODUCT))
            .deleted();
    }

    @Transactional
    public void updateProduct(ProductUpdateRequest productUpdateRequest) {
        Product originProduct = productRepository.findById(productUpdateRequest.getId())
            .orElseThrow(() -> new ErrorException(NOT_FOUND_PRODUCT));
        byte[] decodeImage;

        try {
            if (productUpdateRequest.getFile().isEmpty()) {
                decodeImage = Base64.getDecoder()
                    .decode(productUpdateRequest.getBase64EncodeImage());
            } else {
                decodeImage = productUpdateRequest.getFile().getBytes();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        originProduct.updateProduct(productUpdateRequest, decodeImage);
    }

    // 전체 조회
    @Transactional(readOnly = true)
    public ProductSearchResult readAllProducts(Pageable pageable) {
        Page<Product> page = productRepository.findNonDeletedProducts(pageable);
        return ProductMapper.fromPageProductResult(
            page.map(ProductMapper::toProductSearchResponse));
    }

    // 상품명으로 검색
    @Transactional(readOnly = true)
    public ProductSearchResult searchProductsByName(String name, Pageable pageable) {
        Page<Product> page = productRepository.findNonDeletedProductsByName(name, pageable);
        if (!page.hasContent()) {
            throw new AlertDetailException(
                ErrorCode.NOT_FOUND_PRODUCT,
                String.format("상품명 '%s'에 해당하는 상품이 존재하지 않습니다.", name),
                "/admin/products/list"
            );
        }
        return ProductMapper.fromPageProductResult(
            page.map(ProductMapper::toProductSearchResponse));
    }

    @Transactional(readOnly = true)
    public Page<ProductListResponse> getAllProductsWithImage(Pageable pageable) {
        Page<Product> productPage = productRepository.findNonDeletedProducts(pageable);
        return ProductMapper.toProductListResponse(productPage);
    }
}
