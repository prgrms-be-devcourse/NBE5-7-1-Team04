package com.example.gridscircles.domain.order.controller;

import com.example.gridscircles.domain.order.dto.OrderDetailResponse;
import com.example.gridscircles.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/{orderId}")
    public String findOrderDetail(@PathVariable Long orderId, Model model) {
        OrderDetailResponse orderDetail = orderService.findOrder(orderId);

        model.addAttribute("orderDetail", orderDetail);

        return "findOrderDetail";
    }
}
