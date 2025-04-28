package com.example.gridscircles.domain.order.service;

import static com.example.gridscircles.global.exception.ErrorCode.NOT_FOUND_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.gridscircles.domain.order.dto.CreateOrdersRequest;
import com.example.gridscircles.domain.order.dto.CreateOrdersRequest.CreateOrdersProductDto;
import com.example.gridscircles.domain.order.dto.CreateOrdersResponse;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.repository.OrderProductRepository;
import com.example.gridscircles.domain.order.repository.OrdersRepository;
import com.example.gridscircles.domain.order.util.mapper.OrdersMapper;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import com.example.gridscircles.global.exception.ErrorException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrdersServiceUnitTest {

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @InjectMocks
    private OrdersService ordersService;

    @Nested
    @DisplayName("주문 생성 테스트")
    class saveOrders {

        @Test
        @DisplayName("성공")
        void order_success() {
            // given
            CreateOrdersRequest createOrdersRequest = new CreateOrdersRequest(
                "test@test.com",
                "test address",
                "12345",
                List.of(
                    new CreateOrdersProductDto(1L, 100)
                )
            );

            Product dummyProduct = Product.builder()
                .name("test")
                .description("test")
                .price(100)
                .build();

            Orders orders = OrdersMapper.fromCreateOrdersRequest(createOrdersRequest);

            when(productRepository.findById(any())).thenReturn(Optional.of(dummyProduct));
            when(ordersRepository.save(any())).thenReturn(orders);
            // when
            CreateOrdersResponse response = ordersService.saveOrders(createOrdersRequest);
            // then
            assertNotNull(response);
            assertThat(response.getOrdersId()).isEqualTo(orders.getId());

            verify(ordersRepository).save(any(Orders.class));
            verify(orderProductRepository).saveAll(anyList());
        }

        @Test
        @DisplayName("입력 받은 상품이 존재하지 않으면 예외 발생")
        void fail_found_product() {
            // given
            CreateOrdersRequest createOrdersRequest = new CreateOrdersRequest(
                "test@test.com",
                "test address",
                "12345",
                List.of(
                    new CreateOrdersProductDto(1L, 100)
                )
            );
            when(productRepository.findById(any())).thenReturn(Optional.empty());
            // when & then
            assertThatThrownBy(() -> ordersService.saveOrders(createOrdersRequest))
                .isInstanceOf(ErrorException.class)
                .satisfies(exception -> {
                    ErrorException errorException = (ErrorException) exception;
                    assertThat(errorException.getErrorCode()).isEqualTo(NOT_FOUND_PRODUCT);
                });
        }
    }
}