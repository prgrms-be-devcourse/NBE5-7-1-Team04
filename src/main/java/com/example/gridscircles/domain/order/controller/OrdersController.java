package com.example.gridscircles.domain.order.controller;

import com.example.gridscircles.domain.order.dto.CreateOrdersDto;
import com.example.gridscircles.domain.order.service.OrdersService;
import com.example.gridscircles.domain.product.dto.ProductDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("")
    public String viewSaveOrders(Model model) {
        // TODO : 상품 목록 조회 후 model에 넣기
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
