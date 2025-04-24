package com.example.gridscircles.domain.product.service;

import com.example.gridscircles.domain.product.dto.ProductSearchResponseDto;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  // 특정 ID에 따른 product 정보
  public ProductSearchResponseDto searchProductWithItems(Long productId) {
    Product product = productRepository.findById(productId).orElseThrow(()
        -> new NoSuchElementException("해당 상품은 존재 하지 않습니다. ID: " + productId));

    return new ProductSearchResponseDto(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getImage(),
        product.getCategory()
    );

  }

  // 전체 상품 조회
  public Page<ProductSearchResponseDto> getAllProducts(Pageable pageable) {
    Page<Product> productPage = productRepository.findAll(pageable);

    return productPage.map(product -> new ProductSearchResponseDto(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getImage(),
        product.getCategory())
    );
  }

}
