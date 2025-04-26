package com.example.gridscircles.domain.product.service;

import com.example.gridscircles.domain.product.dto.ProductCreateRequest;
import com.example.gridscircles.domain.product.dto.ProductResponse;
import com.example.gridscircles.domain.product.dto.ProductSearchResponse;
import com.example.gridscircles.domain.product.dto.ProductUpdateRequest;
import com.example.gridscircles.domain.product.dto.ProductListResponse;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import com.example.gridscircles.domain.product.util.mapper.ProductMapper;
import java.io.IOException;
import java.util.Base64;
import java.util.NoSuchElementException;
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
    public ProductResponse findProductById (Long id){

        Product product = productRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("상품 없음 오류 ! "));

        return ProductMapper.entityToProductResponse(product);

    }

    @Transactional
    public void deleteProductById(Long id){

        productRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("상품 없음 오류 ! "))
            .deleted();
    }

    @Transactional
    public Product updateProduct(ProductUpdateRequest productUpdateRequest, Long id) {

        Product originProduct =  productRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("상품 없음 오류 ! "));

        byte [] decodeImage;

        try{
            if (productUpdateRequest.getFile().isEmpty()){
                decodeImage = Base64.getDecoder().decode(productUpdateRequest.getBase64EncodeImage());
            }else{
                decodeImage = productUpdateRequest.getFile().getBytes();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        originProduct.updateProduct(productUpdateRequest,decodeImage);

        return originProduct;
    }


    // 특정 상품명에 따른 상품 조회 (관리자/이미지X)
    @Transactional(readOnly = true)
    public Page<Product> readProductByName(String productName, Pageable pageable) {
        Page<Product> resultProducts = productRepository.findNonDeletedProductsByName(
            productName, pageable);
        if (!resultProducts.hasContent()){
            throw new NoSuchElementException("상품을 찾을 수 없습니다.");
        }
        return resultProducts;
    }

    // 전체 상품 조회 (관리자/이미지X)
    @Transactional(readOnly = true)
    public Page<ProductSearchResponse> readAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findNonDeletedProducts(pageable);

        return productPage.map(ProductMapper::toProductSearchResponse);

    }

    @Transactional(readOnly = true)
    public Page<ProductListResponse> getAllProductsWithImage(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);

        return ProductMapper.toProductListResponse(productPage);
    }
}
