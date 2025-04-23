package com.example.gridscircles.domain.order.service;

import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.repository.OrdersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public Page<Orders> getOrdersByEmail(String email, Pageable pageable) {
        return ordersRepository.findByEmailOrderByCreatedAtDesc(email, pageable);
    }

    public List<Orders> getOrderById(Long id, String email) {
        return ordersRepository.findByIdAndEmailOrderByCreatedAt(id, email);
    }
}
