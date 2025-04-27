package com.example.gridscircles.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.enums.Category;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


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
        @DisplayName("상품명으로 상품 목록 조회 (관리자)")
        void test_getProductsByName() throws Exception {

            // given
            byte[] dummyImage1 = "dummy-image-1".getBytes();
            byte[] dummyImage2 = "dummy-image-2".getBytes();
            byte[] dummyImage3 = "dummy-image-3".getBytes();

            Product product1 = Product.builder()
                .name("딸기라떼")
                .price(1000)
                .category(Category.DRINK)
                .description("맛있어요!")
                .contentType("image/jpeg")
                .image(dummyImage1)
                .del_yn("N")
                .build();
            Product product2 = Product.builder()
                .name("초코라떼")
                .price(2000)
                .category(Category.DRINK)
                .description("맛있어요!!")
                .contentType("image/jpeg")
                .image(dummyImage2)
                .del_yn("N")
                .build();
            Product product3 = Product.builder()
                .name("아메리카노")
                .price(3000)
                .category(Category.DRINK)
                .description("맛있어요!!!")
                .contentType("image/jpeg")
                .image(dummyImage3)
                .del_yn("Y")
                .build();
            productRepository.saveAll(List.of(product1, product2, product3));

            // when
            PageRequest pageable = PageRequest.of(0, 2);
            Page<Product> result = productService.readProductByName("라떼", pageable);

            // then
            assertThat(result.getTotalElements()).isEqualTo(2);
            assertThat(result.getContent()).hasSize(2);
        }

        @Test
        @DisplayName("존재하지 않는 상품명인 경우 예외 처리")
        void test_readProductByName_notFound() throws Exception {
            String nonExistentProductName = "없는상품";
            // when & then: 상품명을 조회했을 때 예외 발생
            assertThatThrownBy(() -> productService.readProductByName(nonExistentProductName,
                PageRequest.of(0, 2)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("상품을 찾을 수 없습니다.");
        }
    }
}
