package com.example.gridscircles.domain.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.gridscircles.domain.order.dto.OrderDetailResponse;
import com.example.gridscircles.domain.order.dto.OrderProductDetailResponse;
import com.example.gridscircles.domain.order.dto.OrderUpdateRequest;
import com.example.gridscircles.domain.order.dto.OrdersSearchResponse;
import com.example.gridscircles.domain.order.entity.OrderProduct;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.enums.OrderStatus;
import com.example.gridscircles.domain.order.exception.OrderNotFoundException;
import com.example.gridscircles.domain.order.repository.OrderProductRepository;
import com.example.gridscircles.domain.order.repository.OrdersRepository;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.enums.Category;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import com.example.gridscircles.global.exception.AlertDetailException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class OrderServiceTests {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Test
    @DisplayName("주문 상세 조회 테스트 - 여러 상품")
    void test_find_orderDetails_multipleProducts() throws Exception {
        // given: 두 개의 상품 생성 및 저장
        byte[] dummyImage1 = "dummy-image-1".getBytes();
        byte[] dummyImage2 = "dummy-image-2".getBytes();

        Product product1 = Product.builder()
            .name("사바하 커피1")
            .price(1000)
            .category(Category.DRINK)
            .description("맛있어요!")
            .contentType("image/jpeg")
            .image(dummyImage1)
            .build();

        Product product2 = Product.builder()
            .name("사바하 커피2")
            .price(1500)
            .category(Category.DRINK)
            .description("최고예요!")
            .contentType("image/jpeg")
            .image(dummyImage2)
            .build();

        productRepository.save(product1);
        productRepository.save(product2);

        // given: 주문 생성
        Orders order = Orders.builder()
            .email("kkkk@gmail.com")
            .address("서울")
            .zipcode("12345")
            .totalPrice(0)
            .orderStatus(OrderStatus.PROCESSING)
            .build();
        ordersRepository.save(order);

        // given: 두 개의 OrderProduct 저장
        OrderProduct op1 = OrderProduct.builder()
            .product(product1)
            .price(product1.getPrice())
            .quantity(2)
            .orders(order)
            .build();

        OrderProduct op2 = OrderProduct.builder()
            .product(product2)
            .price(product2.getPrice())
            .quantity(3)
            .orders(order)
            .build();

        orderProductRepository.save(op1);
        orderProductRepository.save(op2);
        // when
        OrderDetailResponse response = ordersService.getOrderDetail(order.getId());

        assertThat(response).isNotNull();
        assertThat(response.getOrderProducts()).hasSize(2);

        assertThat(response.getOrderProducts())
            .extracting(OrderProductDetailResponse::getProductName)
            .containsExactlyInAnyOrder("사바하 커피1", "사바하 커피2");
        // 총 수량 = 2 + 3 = 5
        assertThat(response.getTotalQuantity()).isEqualTo(5);
        // 총 가격 = (1000 * 2) + (1500 * 3) = 2000 + 4500 = 6500
        assertThat(response.getTotalPrice()).isEqualTo(6500);
        assertThat(response.getAddress()).isEqualTo("서울");
        assertThat(response.getOrderStatus()).isEqualTo(OrderStatus.PROCESSING);
    }

    @Test
    @DisplayName("주문 상세 조회 테스트 - 존재하지 않는 주문 ID 예외 발생")
    void test_find_orderDetails_nonExistingOrder_throwsException() {
        Long nonExistingOrderId = 999L;
        // when, then
        OrderNotFoundException ex = assertThrows(
            OrderNotFoundException.class,
            () -> ordersService.getOrderDetail(nonExistingOrderId)
        );
        assertThat(ex.getMessage()).isEqualTo("주문 정보를 조회할 수 없습니다.");
    }

    @Test
    @DisplayName("주문 수정 테스트")
    void test_updateOrder() {
        // given: 주문 생성
        Orders order = Orders.builder()
            .email("Yuhan@example.com")
            .address("서울특별시 강남구 사바하아파트 444동 444호")
            .zipcode("44444")
            .totalPrice(0)
            .orderStatus(OrderStatus.PROCESSING)
            .build();
        ordersRepository.save(order);

        OrderUpdateRequest updateDto = OrderUpdateRequest.builder()
            .address("서울특별시 용산구 사바하아파트 444동 444호")
            .zipcode("12345")
            .build();
        // when
        ordersService.updateOrder(order.getId(), updateDto);
        // then
        Orders updated = ordersRepository.findById(order.getId())
            .orElseThrow();
        assertThat(updated.getAddress()).isEqualTo("서울특별시 용산구 사바하아파트 444동 444호");
        assertThat(updated.getZipcode()).isEqualTo("12345");
    }

    @Test
    @DisplayName("주문 취소 테스트")
    void test_cancelOrder() {
        // given: 주문 생성
        Orders order = Orders.builder()
            .email("kkkk@gmail.com")
            .address("서울")
            .zipcode("12345")
            .totalPrice(1000 * 2 + 1500 * 3)
            .orderStatus(OrderStatus.PROCESSING)
            .build();
        ordersRepository.save(order);
        // when: 주문 취소
        ordersService.cancelOrder(order.getId());
        // then: 주문 취소 상태 확인
        Orders canceledOrder = ordersRepository.findById(order.getId()).orElseThrow();
        assertThat(canceledOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Nested
    @Transactional
    @DisplayName("주문 내역 조회 테스트 (관리자)")
    class GetOrdersAdminTests {

        @Test
        @DisplayName("특정 주문 ID로 주문 목록 조회 테스트 (관리자)")
        void test_readOrderById() throws Exception {
            // given
            byte[] dummyImage1 = "dummy-image-1".getBytes();

            Product product = Product.builder()
                .name("사바하 커피1")
                .price(1000)
                .category(Category.DRINK)
                .description("맛있어요!")
                .contentType("image/jpeg")
                .image(dummyImage1)
                .del_yn("Y")
                .build();
            productRepository.save(product);

            Orders order = Orders.builder()
                .email("Yuhan@example.com")
                .address("서울특별시 강남구 사바하아파트 444동 444호")
                .zipcode("44444")
                .totalPrice(0)
                .orderStatus(OrderStatus.PROCESSING)
                .build();
            ordersRepository.save(order);

            OrderProduct op2 = OrderProduct.builder()
                .product(product)
                .price(product.getPrice())
                .quantity(2)
                .orders(order)
                .build();
            orderProductRepository.save(op2);

            Long orderId = order.getId();
            // when
            OrdersSearchResponse response = ordersService.readOrderById(orderId);
            // then
            assertThat(response.getOrderId()).isEqualTo(orderId);
            assertThat(response.getProducts()).hasSize(1);
            assertThat(response.getProducts().get(0).getProductName()).isEqualTo("사바하 커피1");
            assertThat(response.getProducts().get(0).getQuantity()).isEqualTo(2);
        }

        @Test
        @DisplayName("존재하지 않는 주문 ID로 주문 목록 조회 시 예외 발생 테스트 (관리자)")
        void test_readOrderById_notFound() throws Exception {
            // given
            Long nonExistentOrderId = 99999L; // 존재하지 않는 주문 ID
            // when & then
            assertThatThrownBy(() -> ordersService.readOrderById(nonExistentOrderId))
                .isInstanceOf(AlertDetailException.class)
                .hasMessageContaining(String.format("주문 ID %d는 존재하지 않습니다.", nonExistentOrderId));
        }
    }
}
