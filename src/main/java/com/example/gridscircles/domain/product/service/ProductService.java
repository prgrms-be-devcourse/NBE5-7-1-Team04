package com.example.gridscircles.domain.product.service;

import com.example.gridscircles.domain.product.dto.ProductForm;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

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

            return repository.save(product);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Product findProductById (Long id){
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("상품 없음 오류 ! "));
    }

    public void deleteProductById(Long id){
        repository.deleteById(id);
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
