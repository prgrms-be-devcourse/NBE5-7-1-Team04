package com.example.gridscircles.domain.order.controller;

import com.example.gridscircles.domain.order.dto.OrderDetailDto;
import com.example.gridscircles.domain.order.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("/{orderId}")
    public String viewOrderDetail(@PathVariable Long orderId, Model model) {

        OrderDetailDto orderDetail = ordersService.getOrderDetail(orderId);

        model.addAttribute("orderDetail", orderDetail);

        return "view_orderDetail";
    }
}
