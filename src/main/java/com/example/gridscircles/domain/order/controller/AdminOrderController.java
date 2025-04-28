package com.example.gridscircles.domain.order.controller;


import static java.util.List.of;

import com.example.gridscircles.domain.order.dto.OrderDetailResponse;
import com.example.gridscircles.domain.order.dto.OrdersSearchResponse;
import com.example.gridscircles.domain.order.service.OrdersService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrdersService ordersService;

    @GetMapping("/orders/list")
    public String viewAdminOrders(
        @RequestParam(required = false) Long orderId, Model model,
        RedirectAttributes redirectAttributes,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {
        if (orderId != null) {
                OrdersSearchResponse response = ordersService.readOrderById(orderId);
                List<OrdersSearchResponse> responselist = of(response);

                model.addAttribute("orders_list", responselist);
                model.addAttribute("hasData", true);
        } else {
            Page<OrdersSearchResponse> responseDtoPage = ordersService
                .getAllOrders(PageRequest.of(page, size));

            model.addAttribute("orders_list", responseDtoPage.getContent());
            model.addAttribute("hasData", responseDtoPage.hasContent());
            model.addAttribute("currentPage", responseDtoPage.getNumber());
            model.addAttribute("totalPages", responseDtoPage.getTotalPages());
            model.addAttribute("pageSize", size);
        }
        return "admin/view_orders";
    }

    @GetMapping("/orders/{orderId}")
    public String viewOrderDetail(@PathVariable Long orderId, Model model) {
        OrderDetailResponse orderDetail = ordersService.getOrderDetail(orderId);
        model.addAttribute("orderDetail", orderDetail);
        return "view_orderDetail";
    }
}
