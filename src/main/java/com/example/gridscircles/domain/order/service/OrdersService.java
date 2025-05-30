package com.example.gridscircles.domain.order.service;

import static com.example.gridscircles.global.exception.ErrorCode.NOT_FOUND_EMAIL;
import static com.example.gridscircles.global.exception.ErrorCode.NOT_FOUND_ORDERS;
import static com.example.gridscircles.global.exception.ErrorCode.NOT_FOUND_PRODUCT;

import com.example.gridscircles.domain.order.dto.CreateOrdersRequest;
import com.example.gridscircles.domain.order.dto.CreateOrdersRequest.CreateOrdersProductDto;
import com.example.gridscircles.domain.order.dto.CreateOrdersResponse;
import com.example.gridscircles.domain.order.dto.OrderDetailResponse;
import com.example.gridscircles.domain.order.dto.OrderSearchResult;
import com.example.gridscircles.domain.order.dto.OrderUpdateRequest;
import com.example.gridscircles.domain.order.dto.OrdersSearchResponse;
import com.example.gridscircles.domain.order.entity.OrderProduct;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.enums.OrderStatus;
import com.example.gridscircles.domain.order.repository.OrderProductRepository;
import com.example.gridscircles.domain.order.repository.OrdersRepository;
import com.example.gridscircles.domain.order.util.OrdersValidator;
import com.example.gridscircles.domain.order.util.mapper.OrderProductMapper;
import com.example.gridscircles.domain.order.util.mapper.OrdersMapper;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.repository.ProductRepository;
import com.example.gridscircles.global.exception.AlertDetailException;
import com.example.gridscircles.global.exception.ErrorCode;
import com.example.gridscircles.global.exception.ErrorException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    @Lazy
    private final OrdersService ordersService;

    // 주문 ID로 검색
    @Transactional(readOnly = true)
    public OrderSearchResult readOrderById(Long orderId) {
        Orders orders = ordersRepository.findById(orderId)
            .orElseThrow(() -> new AlertDetailException(ErrorCode.NOT_FOUND_ORDERS,
                String.format("주문 ID %d는 존재하지 않습니다.", orderId), "/admin/orders/list"));

        List<OrderProduct> orderProducts = orderProductRepository.findByOrdersId(orderId);
        OrdersSearchResponse response = OrdersMapper.toOrdersSearchResponse(orders, orderProducts);

        return OrdersMapper.fromSingleOrderSearchResult(response);
    }

    // 전체 조회
    @Transactional(readOnly = true)
    public OrderSearchResult readAllOrders(Pageable pageable) {
        Page<Orders> ordersPage = ordersRepository.findAllByOrderByCreatedAtDesc(pageable);

        Page<OrdersSearchResponse> responsePage = ordersPage.map(order -> {
            List<OrderProduct> products = orderProductRepository.findByOrdersId(order.getId());
            return OrdersMapper.toOrdersSearchResponse(order, products);
        });

        return OrdersMapper.fromPageOrderSearchResult(responsePage);
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetail(Long orderId) {
        List<OrderProduct> products = orderProductRepository
            .findByOrdersIdWithProductAndOrder(orderId);

        Orders findOrder = products.stream()
            .findFirst()
            .map(OrderProduct::getOrders)
            .orElseThrow(() -> new ErrorException(NOT_FOUND_ORDERS));

        return OrdersMapper.toOrderDetailResponse(findOrder, products, calculateQuantity(products),
            calculateTotalPrice(products));
    }

    private static int calculateTotalPrice(List<OrderProduct> products) {
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
            .orElseThrow(() -> new ErrorException(NOT_FOUND_ORDERS));

        OrdersValidator.validateUpdatable(findOrder.getOrderStatus());

        findOrder.updateOrder(orderUpdateRequest);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Orders findOrder = getOrderById(orderId);

        OrdersValidator.validateCancelable(findOrder.getOrderStatus());
        findOrder.cancel();
    }

    private Orders getOrderById(Long orderId) {
        return ordersRepository.findById(orderId)
            .orElseThrow(() -> new ErrorException(NOT_FOUND_ORDERS));
    }

    @Transactional
    public CreateOrdersResponse saveOrders(CreateOrdersRequest createOrdersRequest) {
        Orders createOrders = OrdersMapper.fromCreateOrdersRequest(createOrdersRequest);
        List<OrderProduct> orderProducts = createOrderProducts(createOrdersRequest.getProducts(),
            createOrders);

        int totalPrice = orderProducts.stream()
            .mapToInt(orderProduct -> orderProduct.getPrice() * orderProduct.getQuantity())
            .sum();

        createOrders.updateTotalPrice(totalPrice);
        ordersRepository.save(createOrders);
        orderProductRepository.saveAll(orderProducts);

        return OrdersMapper.toCreateOrdersResponse(createOrders);
    }

    public Page<Orders> getOrdersByEmail(String email, Pageable pageable) {
        Page<Orders> orders = ordersRepository.findByEmailOrderByCreatedAtDesc(email, pageable);
        if (orders.isEmpty()) {
            throw new ErrorException(NOT_FOUND_EMAIL);
        }
        return orders;
    }

    public List<Orders> getOrderById(Long id, String email) {
        List<Orders> orders = ordersRepository.findByIdAndEmailOrderByCreatedAt(id, email);
        if (orders.isEmpty()) {
            throw new ErrorException(NOT_FOUND_ORDERS);
        }
        return orders;
    }

    private List<OrderProduct> createOrderProducts(List<CreateOrdersProductDto> productsDto,
        Orders order) {
        return productsDto.stream()
            .map(dto -> {
                Product product = productRepository.findById(dto.getId())
                    .orElseThrow(() -> new ErrorException(NOT_FOUND_PRODUCT));

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
