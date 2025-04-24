package com.example.gridscircles.domain.order.controller;

import com.example.gridscircles.domain.order.dto.CreateOrdersDto;
import com.example.gridscircles.domain.order.dto.EmailDto;
import com.example.gridscircles.domain.order.dto.OrderDetailDto;
import com.example.gridscircles.domain.order.entity.Orders;
import com.example.gridscircles.domain.order.service.OrdersService;
import com.example.gridscircles.domain.product.dto.ProductDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("/{orderId}")
    public String viewOrderDetail(@PathVariable Long orderId, Model model) {
        OrderDetailDto orderDetail = ordersService.getOrderDetail(orderId);
        model.addAttribute("orderDetail", orderDetail);
        return "view_orderDetail";
    }

    @GetMapping("/search")
    public String searchForm() {
        return "email_form";
    }

    @GetMapping("/email")
    public String viewOrders(@Validated @ModelAttribute EmailDto request,
        BindingResult bindingResult, @RequestParam(defaultValue = "0") int page, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "이메일 형식이 올바르지 않습니다.");
            return "email_form";
        }
        String email = request.getEmail();
        Page<Orders> ordersPage = ordersService.getOrdersByEmail(email, PageRequest.of(page, 6));

        model.addAttribute("email", email);
        model.addAttribute("orders", ordersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ordersPage.getTotalPages());
        model.addAttribute("request", "email");

        return "view_orders";
    }

    @GetMapping("/id")
    public String viewOrderById(@RequestParam Long orderId,
        @RequestParam(required = false) String email, Model model) {
        List<Orders> orders = ordersService.getOrderById(orderId, email);

        model.addAttribute("orders", orders);
        model.addAttribute("request", "id");

        return "view_orders";
    }

    @GetMapping("")
    public String viewSaveOrders(Model model) {
        model.addAttribute("products", List.of(
            ProductDto.builder()
                .id(1L)
                .price(1000)
                .name("Product 1")
                .category("커피콩")
                .imageType("image/jpeg")
                .imageBase64("/9j/4AAQSkZJRgABAQAAAQABAAD")
                .build())
        );

        return "view_save_orders";
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Long> saveOrders(
        @Validated @RequestBody CreateOrdersDto createOrdersDto) {
        Long ordersId = ordersService.saveOrders(createOrdersDto);
        return ResponseEntity.ok(ordersId);
    }
}