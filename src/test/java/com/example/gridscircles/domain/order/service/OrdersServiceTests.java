package com.example.gridscircles.domain.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.gridscircles.domain.order.dto.OrderDetailResponse;
import com.example.gridscircles.domain.order.dto.OrderProductDetailResponse;
import com.example.gridscircles.domain.order.dto.OrderSearchResult;
import com.example.gridscircles.domain.order.dto.OrderUpdateRequest;
import com.example.gridscircles.domain.order.dto.OrdersSearchResponse;
import com.example.gridscircles.domain.order.entity.OrderProduct;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.enums.OrderStatus;
import com.example.gridscircles.domain.order.repository.OrderProductRepository;
import com.example.gridscircles.domain.order.repository.OrdersRepository;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.enums.Category;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import com.example.gridscircles.global.exception.AlertDetailException;
import com.example.gridscircles.global.exception.ErrorException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("OrderService 기능 테스트")
class OrdersServiceTests {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = saveProduct("사바하 커피1", 1000);
        product2 = saveProduct("사바하 커피2", 1500);
    }

    private Product saveProduct(String name, int price) {
        Product product = Product.builder()
            .name(name)
            .price(price)
            .category(Category.DRINK)
            .description("사바하 커피는 진자 맛있어요!")
            .contentType("image/jpeg")
            .image((name + "-img").getBytes())
            .delYN("N")
            .build();
        return productRepository.save(product);
    }

    private Orders saveOrder(String email, String address, String zipcode, OrderStatus status,
        int totalPrice) {
        Orders order = Orders.builder()
            .email(email)
            .address(address)
            .zipcode(zipcode)
            .totalPrice(totalPrice)
            .orderStatus(status)
            .build();
        return ordersRepository.save(order);
    }

    private void addOrderProduct(Orders order, Product product, int quantity) {
        OrderProduct op = OrderProduct.builder()
            .product(product)
            .price(product.getPrice())
            .quantity(quantity)
            .orders(order)
            .build();
        orderProductRepository.save(op);
    }

    @Nested
    @DisplayName("주문 상세 조회 테스트")
    class GetOrderDetailTests {

        @Test
        @DisplayName("상품 조회 성공")
        void getOrderDetailSuccess() {
            Orders order = saveOrder("Yuhan1@example.com", "서울", "12345", OrderStatus.PROCESSING,
                0);
            addOrderProduct(order, product1, 2);
            addOrderProduct(order, product2, 3);

            OrderDetailResponse orderDetailResponse = ordersService.getOrderDetail(order.getId());

            assertThat(orderDetailResponse).isNotNull();
            assertThat(orderDetailResponse.getOrderProducts()).hasSize(2)
                .extracting(OrderProductDetailResponse::getProductName)
                .containsExactlyInAnyOrder("사바하 커피1", "사바하 커피2");
            assertThat(orderDetailResponse.getTotalQuantity()).isEqualTo(5);
            assertThat(orderDetailResponse.getTotalPrice()).isEqualTo(6500);
            assertThat(orderDetailResponse.getAddress()).isEqualTo("서울");
            assertThat(orderDetailResponse.getOrderStatus()).isEqualTo(OrderStatus.PROCESSING);
        }

        @ParameterizedTest
        @ValueSource(longs = {-1L, 0L, 999L})
        @DisplayName("유효하지 않은 주문 ID 예외 발생")
        void invalidIdThrows(long invalidId) {
            assertThrows(
                ErrorException.class,
                () -> ordersService.getOrderDetail(invalidId),
                "주문 정보를 조회할 수 없습니다."
            );
        }
    }

    @Nested
    @DisplayName("주문 수정 테스트")
    class UpdateOrderTests {

        @Test
        void updateOrderSuccess() {
            Orders order = saveOrder(
                "Yuhan2@example.com",
                "경기도 사바하시 사바하구",
                "11111",
                OrderStatus.PROCESSING,
                0
            );

            OrderUpdateRequest req = OrderUpdateRequest.builder()
                .address("서울특별시 용산구 사바하아파트 202동 202호")
                .zipcode("22222")
                .build();
            ordersService.updateOrder(order.getId(), req);

            Orders updated = ordersRepository.findById(order.getId()).orElseThrow();
            assertThat(updated.getAddress()).isEqualTo("서울특별시 용산구 사바하아파트 202동 202호");
            assertThat(updated.getZipcode()).isEqualTo("22222");
        }

        @ParameterizedTest
        @EnumSource(value = OrderStatus.class, names = {"COMPLETED", "CANCELED"})
        void invalidUpdateOrder(OrderStatus status) {
            Orders order = saveOrder(
                "Yuhan2@example.com",
                "경기도 사바하시 사바하구",
                "11111",
                status,
                0
            );
            OrderUpdateRequest req = OrderUpdateRequest.builder()
                .address("서울특별시 용산구 사바하아파트 202동 202호")
                .zipcode("22222")
                .build();

            assertThrows(
                ErrorException.class,
                () -> ordersService.updateOrder(order.getId(), req),
                "배송이 완료되거나 주문이 취소된 상태면 주문을 수정하실 수 없습니다."
            );
        }
    }


    @Nested
    @DisplayName("주문 취소 테스트")
    class CancelOrderTests {

        @Test
        @DisplayName("주문 취소 성공")
        void cancelOrderSuccess() {
            int total = product1.getPrice() * 2 + product2.getPrice() * 3;
            Orders order = saveOrder("Yuhan3@example.com", "부산", "33333", OrderStatus.PROCESSING,
                total);
            ordersService.cancelOrder(order.getId());

            Orders canceled = ordersRepository.findById(order.getId()).orElseThrow();
            assertThat(canceled.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
        }

        @ParameterizedTest
        @EnumSource(value = OrderStatus.class, names = {"COMPLETED", "CANCELED"})
        @DisplayName("주문 취소 실패(배송 완료 또는 이미 취소된 경우)")
        void invalidCancelOrder(OrderStatus status) {
            int total = product1.getPrice() * 2 + product2.getPrice() * 3;

            Orders order = saveOrder(
                "Yuhan3@example.com",
                "부산",
                "33333",
                status,
                total
            );

            assertThrows(
                ErrorException.class,
                () -> ordersService.cancelOrder(order.getId()),
                "배송이 완료되거나 주문이 취소된 상태면 주문을 취소하실 수 없습니다."
            );
        }
    }

    @Nested
    @DisplayName("주문 내역 조회 테스트")
    class GetOrdersTests {

        @Test
        @DisplayName("이메일로 전체 주문 내역 조회")
        void getOrdersByEmailTest() {
            String email = "test@example.com";
            Orders order1 = Orders.builder()
                .email(email)
                .address("서울")
                .zipcode("12345")
                .totalPrice(5000)
                .orderStatus(OrderStatus.PROCESSING)
                .build();

            Orders order2 = Orders.builder()
                .email(email)
                .address("서울2")
                .zipcode("67890")
                .totalPrice(8000)
                .orderStatus(OrderStatus.PROCESSING)
                .build();
            ordersRepository.saveAll(List.of(order1, order2));

            Page<Orders> result = ordersService.getOrdersByEmail(email, PageRequest.of(0, 10));

            assertThat(result.getContent()).hasSize(2);
            assertThat(result.getContent().getFirst().getEmail()).isEqualTo(email);
        }

        @Test
        @DisplayName("전체 주문 내역에서 주문 ID로 특정 주문 내역 조회")
        void getOrdersByIdTest() {
            String email = "test@example.com";
            Orders order = Orders.builder()
                .email(email)
                .address("부산")
                .zipcode("54321")
                .totalPrice(10000)
                .orderStatus(OrderStatus.PROCESSING)
                .build();
            Orders savedOrder = ordersRepository.save(order);

            List<Orders> result = ordersService.getOrderById(savedOrder.getId(), email);

            assertThat(result).hasSize(1);
            assertThat(result.getFirst().getId()).isEqualTo(savedOrder.getId());
            assertThat(result.getFirst().getEmail()).isEqualTo(email);
        }
    }


    @Nested
    @Transactional
    @DisplayName("주문 내역 조회 테스트 (관리자)")
    class GetOrdersAdminTests {

                @Test
        @DisplayName("searchResult - 주문 ID로 단건 조회 성공 테스트")
        void test_searchResult_singleOrder() {
            // given
            byte[] dummyImage = "dummy-image-2".getBytes();

            Product product = Product.builder()
                .name("사바하 커피2")
                .price(1500)
                .category(Category.DRINK)
                .description("또 맛있어요!")
                .contentType("image/jpeg")
                .image(dummyImage)
                .delYN("N")
                .build();
            productRepository.save(product);

            Orders order = Orders.builder()
                .email("yuna@example.com")
                .address("서울특별시 서초구 어딘가")
                .zipcode("55555")
                .totalPrice(3000)
                .orderStatus(OrderStatus.PROCESSING)
                .build();
            ordersRepository.save(order);

            OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .price(product.getPrice())
                .quantity(2)
                .orders(order)
                .build();
            orderProductRepository.save(orderProduct);

            Long orderId = order.getId();
            // when
            OrderSearchResult result = ordersService.readOrderById(orderId);

            // then
            assertThat(result.getOrdersList()).hasSize(1);
            assertThat(result.getOrdersList().get(0).getOrderId()).isEqualTo(orderId);
            assertThat(result.isHasData()).isTrue();
            assertThat(result.getSize()).isEqualTo(1);
        }

        @Test
        @DisplayName("searchResult - 전체 주문 목록 조회 성공 테스트")
        void test_searchResult_allOrders() {
            // given
            byte[] dummyImage = "dummy-image-3".getBytes();

            Product product = Product.builder()
                .name("사바하 커피3")
                .price(2000)
                .category(Category.DRINK)
                .description("또또 맛있어요!")
                .contentType("image/jpeg")
                .image(dummyImage)
                .delYN("N")
                .build();
            productRepository.save(product);

            Orders order = Orders.builder()
                .email("junho@example.com")
                .address("서울특별시 마포구 어딘가")
                .zipcode("66666")
                .totalPrice(4000)
                .orderStatus(OrderStatus.PROCESSING)
                .build();
            ordersRepository.save(order);

            OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .price(product.getPrice())
                .quantity(2)
                .orders(order)
                .build();
            orderProductRepository.save(orderProduct);
            Pageable pageable = PageRequest.of(0, 5);
            // when
            OrderSearchResult result = ordersService.readAllOrders(pageable);

            // then
            assertThat(result.getOrdersList()).isNotEmpty();
            assertThat(result.isHasData()).isTrue();
            assertThat(result.getCurrentPage()).isEqualTo(0);
        }

        @Test
        @DisplayName("존재하지 않는 주문 ID로 주문 목록 조회 시 예외 발생 테스트 (관리자)")
        void test_readOrderById_notFound() throws Exception {
            Long nonExistentOrderId = 99999L;
            assertThatThrownBy(() -> ordersService.readOrderById(nonExistentOrderId))
                .isInstanceOf(AlertDetailException.class)
                .hasMessageContaining(String.format("주문 ID %d는 존재하지 않습니다.", nonExistentOrderId));

        }
    }
}
