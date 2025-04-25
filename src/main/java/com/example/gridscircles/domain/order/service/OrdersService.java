package com.example.gridscircles.domain.order.service;

import com.example.gridscircles.domain.order.dto.CreateOrdersRequest;
import com.example.gridscircles.domain.order.dto.CreateOrdersRequest.CreateOrdersProductDto;
import com.example.gridscircles.domain.order.dto.CreateOrdersResponse;
import com.example.gridscircles.domain.order.dto.OrderDetailResponse;
import com.example.gridscircles.domain.order.dto.OrderUpdateRequest;
import com.example.gridscircles.domain.order.entity.OrderProduct;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.enums.OrderStatus;
import com.example.gridscircles.domain.order.exception.OrderNotFoundException;
import com.example.gridscircles.domain.order.repository.OrderProductRepository;
import com.example.gridscircles.domain.order.repository.OrdersRepository;
import com.example.gridscircles.domain.order.util.mapper.OrderProductMapper;
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

        Orders findOrder = getOrderById(orderId);

        findOrder.updateOrder(orderUpdateRequest);
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Orders findOrder = getOrderById(orderId);

        validateOrderStatus(findOrder);
        findOrder.cancel();
    }

    private static void validateOrderStatus(Orders findOrder) {
        if (findOrder.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new OrderNotFoundException("배송이 완료된 주문은 취소할 수 없습니다.");
        }
    }

    private Orders getOrderById(Long orderId) {
        return ordersRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("주문 정보를 찾을 수 없습니다."));
    }

    @Transactional
    public CreateOrdersResponse saveOrders(CreateOrdersRequest createOrdersRequest) {
        Orders createOrders = OrdersMapper.fromCreateOrdersRequest(createOrdersRequest);
        List<OrderProduct> orderProducts = createOrderProducts(createOrdersRequest.getProducts(), createOrders);
        
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
        ordersRepository.updateOrdersStatusByOrderStatus(OrderStatus.PROCESSING, OrderStatus.COMPLETED);
    }
}