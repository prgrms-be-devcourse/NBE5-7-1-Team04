package com.example.gridscircles.domain.order.controller;



import com.example.gridscircles.domain.order.dto.OrderDetailResponse;
import com.example.gridscircles.domain.order.dto.OrderSearchResult;
import com.example.gridscircles.domain.order.dto.OrdersSearchResponse;
import com.example.gridscircles.domain.order.service.OrdersService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {
        OrderSearchResult searchResult = ordersService.readAllOrders(PageRequest.of(page,size));

        model.addAttribute("orders_list", searchResult.getOrdersList());
        model.addAttribute("hasData", searchResult.isHasData());
        model.addAttribute("currentPage", searchResult.getCurrentPage());
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("pageSize", searchResult.getSize());
        return "admin/view_orders";
    }

    // 주문 ID로 검색
    @GetMapping("/search")
    public String searchOrderById(
        @RequestParam("order") Long orderId,
        Model model
    ) {
        OrderSearchResult result = ordersService.readOrderById(orderId);

        model.addAttribute("orders_list", result.getOrdersList());
        model.addAttribute("currentPage", result.getCurrentPage());
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("pageSize", result.getSize());
        model.addAttribute("hasData", result.isHasData());
        return "admin/view_orders";
    }

    @GetMapping("/orders/{orderId}")
    public String viewOrderDetail(@PathVariable Long orderId, Model model) {
        OrderDetailResponse orderDetail = ordersService.getOrderDetail(orderId);
        model.addAttribute("orderDetail", orderDetail);
        return "view_orderDetail";
    }
}
