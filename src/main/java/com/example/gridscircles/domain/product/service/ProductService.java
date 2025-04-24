package com.example.gridscircles.domain.product.service;

import com.example.gridscircles.domain.product.dto.ProductForm;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.repository.ProductRepository;

import com.example.gridscircles.domain.product.util.ProductMapper;
import java.io.IOException;
import java.util.Base64;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Transactional
    public Product saveProduct(ProductForm productForm) {

        Product product = ProductMapper.toEntity(productForm);

        return repository.save(product);

    }

    @Transactional(readOnly = true)
    public Product findProductById (Long id){

        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("상품 없음 오류 ! "));

    }

    @Transactional
    public void deleteProductById(Long id){

        findProductById(id).deleted();

    }

    @Transactional
    public Product updateProduct(ProductForm productForm, Long id) {

        Product originProduct = findProductById(id);

        byte [] decodeImage;

        try{
            if (productForm.getFile().isEmpty()){
                decodeImage = Base64.getDecoder().decode(productForm.getBase64EncodeImage());
            }else{
                decodeImage = productForm.getFile().getBytes();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        originProduct.updateProduct(productForm,decodeImage);

        return originProduct;
    }

}
