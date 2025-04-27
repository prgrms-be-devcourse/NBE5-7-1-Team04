package com.example.gridscircles.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gridscircles.domain.product.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1. 삭제되지 않은 상품 중에서, 상품명에 주어진 name을 포함하는 상품을 페이징 처리
    @Query("SELECT p FROM Product p WHERE p.del_yn = 'N' AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Product> findNonDeletedProductsByName(@Param("name") String name, Pageable pageable);

    // 2. 삭제되지 않은 상품 전체를 페이징 처리
    @Query("SELECT p FROM Product p WHERE p.del_yn = 'N'")
    Page<Product> findNonDeletedProducts(Pageable pageable);

    Page<Product> findAll(Pageable pageable);
}
