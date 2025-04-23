package com.example.gridscircles.domain.order.service;

import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.repository.OrdersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public List<Orders> getOrdersByEmail(String email) {
        return ordersRepository.findByEmailOrderByCreatedAtDesc(email);
    }

    public List<Orders> getOrderById(Long id) {
        return ordersRepository.findByIdOrderByCreatedAtDesc(id);
    }
}
