package com.example.gridscircles.domain.order;

import com.example.gridscircles.domain.order.entity.OrderProduct;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.enums.OrderStatus;
import com.example.gridscircles.domain.order.repository.OrderProductRepository;
import com.example.gridscircles.domain.order.repository.OrdersRepository;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.enums.Category;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component("orderDataLoader")
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

  private final ProductRepository productRepository;
  private final OrdersRepository ordersRepository;
  private final OrderProductRepository orderProductRepository;

  @Override
  public void run(String... args) throws Exception {
    // 1) Product 더미 삽입
    Product americano = productRepository.save(
        Product.builder()
            .name("아메리카노")
            .category(Category.COFFEE)
            .description("따뜻한 아메리카노")
            .price(3000)
            .image("americano.jpg")
            .build()
    );
    Product latte = productRepository.save(
        Product.builder()
            .name("카페라떼")
            .category(Category.COFFEE)
            .description("부드러운 카페라떼")
            .price(6000)
            .image("latte.jpg")
            .build()
    );

    // 2) Orders 더미 삽입
    Orders order = ordersRepository.save(
        Orders.builder()
            .email("user@example.com")
            .address("서울시 중구 세종대로")
            .zipcode("04524")
            .totalPrice(12000)
            .orderStatus(OrderStatus.COMPLETED)
            .build()
    );

    // 3) OrderItem 더미 삽입
    OrderProduct item1 = OrderProduct.builder()
        .orders(order)
        .product(americano)
        .quantity(2)
        .price(americano.getPrice())
        .build();
    OrderProduct item2 = OrderProduct.builder()
        .orders(order)
        .product(latte)
        .quantity(1)
        .price(latte.getPrice())
        .build();

    Product americano1 = productRepository.save(
        Product.builder()
            .name("아샷추")
            .category(Category.COFFEE)
            .description("차가운 아샷추")
            .price(5000)
            .image("americano.jpg")
            .build()
    );
    Product latte1 = productRepository.save(
        Product.builder()
            .name("고구마라떼")
            .category(Category.COFFEE)
            .description("부드러운 고구마라떼")
            .price(5000)
            .image("latte.jpg")
            .build()
    );

    // 2) Orders 더미 삽입
    Orders order1 = ordersRepository.save(
        Orders.builder()
            .email("user2@example.com")
            .address("서울시 용산구 원효대로")
            .zipcode("02324")
            .totalPrice(15000)
            .orderStatus(OrderStatus.PROCESSING)
            .build()
    );

    // 3) OrderItem 더미 삽입
    OrderProduct item3 = OrderProduct.builder()
        .orders(order1)
        .product(americano1)
        .quantity(2)
        .price(americano1.getPrice())
        .build();
    OrderProduct item4 = OrderProduct.builder()
        .orders(order1)
        .product(latte1)
        .quantity(1)
        .price(latte1.getPrice())
        .build();

    Product americano3 = productRepository.save(
        Product.builder()
            .name("아메리카노")
            .category(Category.COFFEE)
            .description("따뜻한 아메리카노")
            .price(3000)
            .image("americano.jpg")
            .build()
    );
    // 2) Orders 더미 삽입
    Orders order3 = ordersRepository.save(
        Orders.builder()
            .email("user2@example.com")
            .address("서울시 용산구 원효대로")
            .zipcode("02324")
            .totalPrice(3000)
            .orderStatus(OrderStatus.PROCESSING)
            .build()
    );
    OrderProduct item13 = OrderProduct.builder()
        .orders(order3)
        .product(americano3)
        .quantity(1)
        .price(americano3.getPrice())
        .build();

    Product americano4 = productRepository.save(
        Product.builder()
            .name("아메리카노")
            .category(Category.COFFEE)
            .description("따뜻한 아메리카노")
            .price(3000)
            .image("americano.jpg")
            .build()
    );
    // 2) Orders 더미 삽입
    Orders order4= ordersRepository.save(
        Orders.builder()
            .email("user2@example.com")
            .address("서울시 용산구 원효대로")
            .zipcode("02324")
            .totalPrice(3000)
            .orderStatus(OrderStatus.PROCESSING)
            .build()
    );
    OrderProduct item14 = OrderProduct.builder()
        .orders(order4)
        .product(americano4)
        .quantity(1)
        .price(americano4.getPrice())
        .build();

    Product americano5 = productRepository.save(
        Product.builder()
            .name("아메리카노")
            .category(Category.COFFEE)
            .description("따뜻한 아메리카노")
            .price(3000)
            .image("americano.jpg")
            .build()
    );
    // 2) Orders 더미 삽입
    Orders order5= ordersRepository.save(
        Orders.builder()
            .email("user2@example.com")
            .address("서울시 용산구 원효대로")
            .zipcode("02324")
            .totalPrice(3000)
            .orderStatus(OrderStatus.PROCESSING)
            .build()
    );
    OrderProduct item15 = OrderProduct.builder()
        .orders(order5)
        .product(americano5)
        .quantity(1)
        .price(americano5.getPrice())
        .build();


    Product americano6 = productRepository.save(
        Product.builder()
            .name("아메리카노")
            .category(Category.COFFEE)
            .description("따뜻한 아메리카노")
            .price(3000)
            .image("americano.jpg")
            .build()
    );
    // 2) Orders 더미 삽입
    Orders order6= ordersRepository.save(
        Orders.builder()
            .email("user2@example.com")
            .address("서울시 용산구 원효대로")
            .zipcode("02324")
            .totalPrice(3000)
            .orderStatus(OrderStatus.PROCESSING)
            .build()
    );
    OrderProduct item16 = OrderProduct.builder()
        .orders(order6)
        .product(americano6)
        .quantity(1)
        .price(americano6.getPrice())
        .build();


    Product americano7 = productRepository.save(
        Product.builder()
            .name("아메리카노")
            .category(Category.COFFEE)
            .description("따뜻한 아메리카노")
            .price(3000)
            .image("americano.jpg")
            .build()
    );
    // 2) Orders 더미 삽입
    Orders order7= ordersRepository.save(
        Orders.builder()
            .email("user2@example.com")
            .address("서울시 용산구 원효대로")
            .zipcode("02324")
            .totalPrice(3000)
            .orderStatus(OrderStatus.PROCESSING)
            .build()
    );
    OrderProduct item17 = OrderProduct.builder()
        .orders(order7)
        .product(americano7)
        .quantity(1)
        .price(americano7.getPrice())
        .build();


    Product americano8 = productRepository.save(
        Product.builder()
            .name("아메리카노")
            .category(Category.COFFEE)
            .description("따뜻한 아메리카노")
            .price(3000)
            .image("americano.jpg")
            .build()
    );
    // 2) Orders 더미 삽입
    Orders order8= ordersRepository.save(
        Orders.builder()
            .email("user2@example.com")
            .address("서울시 용산구 원효대로")
            .zipcode("02324")
            .totalPrice(3000)
            .orderStatus(OrderStatus.PROCESSING)
            .build()
    );
    OrderProduct item18 = OrderProduct.builder()
        .orders(order8)
        .product(americano8)
        .quantity(1)
        .price(americano8.getPrice())
        .build();


    Product americano9 = productRepository.save(
        Product.builder()
            .name("아메리카노")
            .category(Category.COFFEE)
            .description("따뜻한 아메리카노")
            .price(3000)
            .image("americano.jpg")
            .build()
    );
    // 2) Orders 더미 삽입
    Orders order9= ordersRepository.save(
        Orders.builder()
            .email("user2@example.com")
            .address("서울시 용산구 원효대로")
            .zipcode("02324")
            .totalPrice(3000)
            .orderStatus(OrderStatus.PROCESSING)
            .build()
    );
    OrderProduct item19 = OrderProduct.builder()
        .orders(order9)
        .product(americano9)
        .quantity(1)
        .price(americano9.getPrice())
        .build();


    Product americano10 = productRepository.save(
        Product.builder()
            .name("아메리카노")
            .category(Category.COFFEE)
            .description("따뜻한 아메리카노")
            .price(3000)
            .image("americano.jpg")
            .build()
    );
    // 2) Orders 더미 삽입
    Orders order10= ordersRepository.save(
        Orders.builder()
            .email("user2@example.com")
            .address("서울시 용산구 원효대로")
            .zipcode("02324")
            .totalPrice(3000)
            .orderStatus(OrderStatus.PROCESSING)
            .build()
    );
    OrderProduct item10 = OrderProduct.builder()
        .orders(order10)
        .product(americano10)
        .quantity(1)
        .price(americano10.getPrice())
        .build();


    orderProductRepository.saveAll(List.of(item1, item2, item3, item4, item13, item14, item10,item15,item16,item17,item18,item19));

    // 4) (선택) cascade 를 이용했다면 ordersRepository.save(order) 로 한 번만 저장해도 됩니다.
    // ordersRepository.save(order);
  }
}