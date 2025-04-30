package com.example.gridscircles.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.gridscircles.domain.product.dto.ProductSearchResult;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.enums.Category;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import com.example.gridscircles.global.exception.AlertDetailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@SpringBootTest
public class ProductsServiceTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("상품 내역 조회 테스트 (관리자)")
    class GetProductsAdminTests {

        @Test
        @DisplayName("상품명으로 검색하여 상품 조회 성공 테스트")
        void test_searchProducts_byName_success() {
            // given
            byte[] dummyImage = "dummy-image-1".getBytes();

            Product product = Product.builder()
                .name("테스트 커피1")
                .price(1000)
                .category(Category.DRINK)
                .description("맛있는 커피입니다")
                .contentType("image/jpeg")
                .image(dummyImage)
                .delYN("N")
                .build();
            productRepository.save(product);

            Pageable pageable = PageRequest.of(0, 5);

            // when
            ProductSearchResult result = productService.searchProductsByName("테스트 커피1", pageable);

            // then
            assertThat(result.getProductsList()).hasSize(1);
            assertThat(result.isHasData()).isTrue();
            assertThat(result.getProductsList().get(0).getName()).isEqualTo("테스트 커피1");
        }

        @Test
        @DisplayName("상품명으로 검색했으나 결과가 없는 경우 예외 발생 테스트")
        void test_searchProducts_byName_notFound() {
            // given
            Pageable pageable = PageRequest.of(0, 5);

            // when & then
            assertThatThrownBy(() -> productService.searchProductsByName("존재하지 않는 상품명", pageable))
                .isInstanceOf(AlertDetailException.class)
                .hasMessageContaining("존재하지 않습니다.");
        }

        @Test
        @DisplayName("전체 상품 목록 조회 성공 테스트")
        void test_searchProducts_all_success() {
            // given
            byte[] dummyImage1 = "dummy-image-1".getBytes();
            byte[] dummyImage2 = "dummy-image-2".getBytes();

            Product product1 = Product.builder()
                .name("테스트 커피2")
                .price(1200)
                .category(Category.DRINK)
                .description("또 다른 커피입니다")
                .contentType("image/jpeg")
                .image(dummyImage1)
                .delYN("N")
                .build();

            Product product2 = Product.builder()
                .name("테스트 브레드")
                .price(1500)
                .category(Category.BREAD)
                .description("맛있는 빵입니다")
                .contentType("image/jpeg")
                .image(dummyImage2)
                .delYN("N")
                .build();

            productRepository.save(product1);
            productRepository.save(product2);

            Pageable pageable = PageRequest.of(0, 5);

            // when
            ProductSearchResult result = productService.readAllProducts(pageable);

            // then
            assertThat(result.getProductsList()).hasSizeGreaterThanOrEqualTo(2);
            assertThat(result.isHasData()).isTrue();
        }
    }
}
