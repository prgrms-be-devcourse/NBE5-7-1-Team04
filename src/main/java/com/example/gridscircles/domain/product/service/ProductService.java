package com.example.gridscircles.domain.product.service;

import com.example.gridscircles.domain.product.dto.ProductSearchResponseDto;
import com.example.gridscircles.domain.product.dto.ProductForm;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
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

  public Product saveProduct(ProductForm productForm) {

    int price =
        Integer.parseInt(productForm.getPrice().replace(",",""));

    try {

      Product product = Product.builder()
          .name(productForm.getName())
          .category(productForm.getCategory())
          .description(productForm.getDescription())
          .price(price)
          .image(productForm.getFile().getBytes())
          .contentType(productForm.getFile().getContentType())
          .build();

      return productRepository.save(product);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Product findProductById (Long id){
    return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("상품 없음 오류 ! "));
  }

  public void deleteProductById(Long id){
    productRepository.deleteById(id);
  }

  public Product updateProduct(ProductForm productForm, Long id) {

    Product originProduct = findProductById(id);

    try {
      originProduct.updateProduct(productForm, productForm.getFile().getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return originProduct;
  }


}
