package com.example.gridscircles.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gridscircles.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByNameIgnoreCase(String name);
    Page<Product> findAll(Pageable pageable);
}
