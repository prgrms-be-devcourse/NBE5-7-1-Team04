package com.example.gridscircles.domain.order.service;

import com.example.gridscircles.domain.order.dto.CreateOrdersDto;
import com.example.gridscircles.domain.order.dto.CreateOrdersDto.CreateOrdersProductDto;
import com.example.gridscircles.domain.order.dto.OrderDetailResponse;
import com.example.gridscircles.domain.order.dto.OrderUpdateRequest;
import com.example.gridscircles.domain.order.entity.OrderProduct;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.exception.OrderNotFoundException;
import com.example.gridscircles.domain.order.repository.OrderProductRepository;
import com.example.gridscircles.domain.order.repository.OrdersRepository;
import com.example.gridscircles.domain.order.util.mapper.OrdersMapper;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetail(Long orderId) {
        List<OrderProduct> products = orderProductRepository
            .findByOrdersIdWithProductAndOrder(orderId);

        Orders findOrder = products.stream()
            .findFirst()
            .map(OrderProduct::getOrders)
            .orElseThrow(() -> new OrderNotFoundException("주문 정보를 조회할 수 없습니다."));

        return OrdersMapper.toOrderDetailResponse(findOrder, products, calculateQuantity(products),
            calculdateTotalPrice(products));
    }

    private static int calculdateTotalPrice(List<OrderProduct> products) {
        return products.stream()
            .mapToInt(op -> op.getPrice() * op.getQuantity())
            .sum();
    }

    private static int calculateQuantity(List<OrderProduct> products) {
        return products.stream()
            .mapToInt(OrderProduct::getQuantity)
            .sum();
    }

    @Transactional
    public void updateOrder(Long orderId, OrderUpdateRequest orderUpdateRequest) {

        Orders findOrder = ordersRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("주문 정보를 찾을 수 없습니다."));

        findOrder.updateOrder(orderUpdateRequest);

        ordersRepository.save(findOrder);
    }


    @Transactional
    public Long saveOrders(CreateOrdersDto createOrdersDto) {
        Orders createOrders = Orders.from(createOrdersDto);
        List<OrderProduct> orderProducts = createOrderProducts(createOrdersDto.getProducts(),
            createOrders);

        int totalPrice = orderProducts.stream()
            .mapToInt(OrderProduct::getPrice)
            .sum();

        createOrders.setTotalPrice(totalPrice);
        orderProductRepository.saveAll(orderProducts);
        return ordersRepository.save(createOrders).getId();
    }

    public Page<Orders> getOrdersByEmail(String email, Pageable pageable) {
        return ordersRepository.findByEmailOrderByCreatedAtDesc(email, pageable);
    }

    public List<Orders> getOrderById(Long id, String email) {
        return ordersRepository.findByIdAndEmailOrderByCreatedAt(id, email);
    }

    private List<OrderProduct> createOrderProducts(List<CreateOrdersProductDto> productsDto,
        Orders order) {
        return productsDto.stream()
            .map(dto -> {
                Product product = productRepository.findById(dto.getId())
                    .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));
                return OrderProduct.builder()
                    .orders(order)
                    .product(product)
                    .quantity(dto.getQuantity())
                    .price(product.getPrice() * dto.getQuantity())
                    .build();
            })
            .toList();
    }
}
