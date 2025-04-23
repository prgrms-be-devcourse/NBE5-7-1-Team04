package com.example.gridscircles.domain.order.controller;

import com.example.gridscircles.domain.order.dto.EmailDto;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.service.OrdersService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("/search")
    public String searchForm() {
        return "email_form";
    }

    @GetMapping
    public String viewOrders(@Validated @ModelAttribute EmailDto request,
        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "이메일 형식이 올바르지 않습니다.");
            return "email_form";
        }
        List<Orders> orders = ordersService.getOrdersByEmail(request.getEmail());

        model.addAttribute("email", request.getEmail());
        model.addAttribute("orders", orders);
        model.addAttribute("request", "email");

        return "view_orders";
    }

    @GetMapping("/id")
    public String viewOrderById(@RequestParam Long orderId, Model model) {
        List<Orders> orders = ordersService.getOrderById(orderId);

        model.addAttribute("orders", orders);
        model.addAttribute("request", "id");

        return "view_orders";
    }
}
