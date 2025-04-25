package com.example.gridscircles.domain.order.controller;


import static java.util.List.of;

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
    public String viewlistOrders(
        @RequestParam(required = false) Long orderId, Model model,
        RedirectAttributes redirectAttributes,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        if (orderId != null) {
            try {
                // orderId 파라미터가 있으면 해당 상품만 리스트로 구성 (Paged = false)
                OrdersSearchResponse response = ordersService.readOrderById(orderId);
                List<OrdersSearchResponse> responselist = of(response);
                boolean hasData = !CollectionUtils.isEmpty(responselist);
                model.addAttribute("orders_list", responselist);
                model.addAttribute("hasData", hasData);

            } catch (NoSuchElementException e) {
                redirectAttributes.addFlashAttribute("errorMessage",
                    "검색한 ID  " + orderId + "  은 존재하지 않는 주문 ID입니다.");
                return "redirect:/admin/orders/list";
            }

        } else {
            // 없으면 전체 주문 목록 (Paged = true)
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


}
