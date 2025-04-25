package com.example.gridscircles.domain.order.service;

import com.example.gridscircles.domain.order.dto.CreateOrdersRequest;
import com.example.gridscircles.domain.order.dto.CreateOrdersRequest.CreateOrdersProductDto;
import com.example.gridscircles.domain.order.dto.CreateOrdersResponse;
import com.example.gridscircles.domain.order.dto.OrderDetailResponse;
import com.example.gridscircles.domain.order.dto.OrderUpdateRequest;
import com.example.gridscircles.domain.order.dto.OrdersSearchResponse;
import com.example.gridscircles.domain.order.dto.ProductInfoResponse;
import com.example.gridscircles.domain.order.entity.OrderProduct;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.enums.OrderStatus;
import com.example.gridscircles.domain.order.exception.OrderNotFoundException;
import com.example.gridscircles.domain.order.repository.OrderProductRepository;
import com.example.gridscircles.domain.order.repository.OrdersRepository;
import com.example.gridscircles.domain.order.util.OrdersValidator;
import com.example.gridscircles.domain.order.util.mapper.OrderProductMapper;
import com.example.gridscircles.domain.order.util.mapper.OrdersMapper;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
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

    // 특정 ID에 product와 order정보 조회
    @Transactional(readOnly = true)
    public OrdersSearchResponse searchOrderWithItems(Long orderId) {
        Orders orders = ordersRepository.findById(orderId).orElseThrow(()
            -> new NoSuchElementException("해당 주문은 존재 하지 않습니다. ID: " + orderId));

        List<ProductInfoResponse> products = orders.getOrderProducts().stream()
            .map(orderProduct -> new ProductInfoResponse(
                orderProduct.getProduct().getName(),
                orderProduct.getQuantity(),
                orderProduct.getPrice()
            )).collect(Collectors.toList());

        return new OrdersSearchResponse(
            orders.getId(),
            orders.getEmail(),
            orders.getAddress(),
            orders.getZipcode(),
            orders.getTotalPrice(),
            orders.getOrderStatus(),
            orders.getCreatedAt(),
            products
        );

    }

    // 전체 주문 조회
    @Transactional(readOnly = true)
    public Page<OrdersSearchResponse> getAllOrders(Pageable pageable) {
        Page<Orders> ordersPage = ordersRepository.findAllByOrderByCreatedAtDesc(pageable);

        return ordersPage.map(order -> new OrdersSearchResponse(
            order.getId(),
            order.getEmail(),
            order.getAddress(),
            order.getZipcode(),
            order.getTotalPrice(),
            order.getOrderStatus(),
            order.getCreatedAt(),
            order.getOrderProducts().stream()
                .map(item -> new ProductInfoResponse(
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getPrice()
                ))
                .collect(Collectors.toList())
        ));


    }

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

        OrdersValidator.validateUpdateable(findOrder.getOrderStatus());
        findOrder.updateOrder(orderUpdateRequest);

        ordersRepository.save(findOrder);
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Orders findOrder = getOrderById(orderId);

        OrdersValidator.validateCancelable(findOrder.getOrderStatus());
        findOrder.cancel();
    }

    private Orders getOrderById(Long orderId) {
        return ordersRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("주문 정보를 찾을 수 없습니다."));
    }

    @Transactional
    public CreateOrdersResponse saveOrders(CreateOrdersRequest createOrdersRequest) {
        Orders createOrders = OrdersMapper.fromCreateOrdersRequest(createOrdersRequest);
        List<OrderProduct> orderProducts = createOrderProducts(createOrdersRequest.getProducts(),
            createOrders);

        int totalPrice = orderProducts.stream()
            .mapToInt(OrderProduct::getPrice)
            .sum();

        createOrders.setTotalPrice(totalPrice);
        ordersRepository.save(createOrders);
        orderProductRepository.saveAll(orderProducts);
        return OrdersMapper.toCreateOrdersResponse(createOrders);
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
                return OrderProductMapper.fromCreateOrdersProductDto(dto, order, product);
            })
            .toList();
    }

    @Transactional
    public void completeOrders() {
        ordersRepository.updateOrdersStatusByOrderStatus(OrderStatus.PROCESSING,
            OrderStatus.COMPLETED);
    }

}
