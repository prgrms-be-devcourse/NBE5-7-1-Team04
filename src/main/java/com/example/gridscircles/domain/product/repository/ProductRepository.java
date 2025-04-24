package com.example.gridscircles.domain.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gridscircles.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findAllByOrderByIdDesc();

}
