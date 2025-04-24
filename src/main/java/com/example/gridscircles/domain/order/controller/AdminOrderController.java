package com.example.gridscircles.domain.order.controller;


import com.example.gridscircles.domain.order.dto.OrdersSearchResponseDto;
import com.example.gridscircles.domain.order.service.OrdersService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
  public String viewlistOrders(
      @RequestParam(required = false) Long orderId, Model model,
      RedirectAttributes redirectAttributes,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {

    if (orderId != null) {
      try {
        // orderId 파라미터가 있으면 해당 상품만 리스트로 구성 (Paged = false)
        OrdersSearchResponseDto response = ordersService.searchOrderWithItems(orderId);
        model.addAttribute("orders_list", List.of(response));
        model.addAttribute("isPaged", false); // 페이징 여부

      } catch (NoSuchElementException e) {
        redirectAttributes.addFlashAttribute("errorMessage",
            "검색한 ID  " + orderId + "  은 존재하지 않는 주문 ID입니다.");
        return "redirect:/admin/orders/list";
      }

    } else {
      // 없으면 전체 주문 목록 (Paged = true)
      Page<OrdersSearchResponseDto> responseDtoPage = ordersService.getAllOrders(
          PageRequest.of(page, size));

      model.addAttribute("orders_list", responseDtoPage);
      model.addAttribute("currentPage", page);
      model.addAttribute("totalPages", responseDtoPage.getTotalPages());
      model.addAttribute("isPaged", true);
      model.addAttribute("pageSize", responseDtoPage.getSize());
    }

    return "admin/orders";
  }

  // 해당 주문의 상세페이지
  @GetMapping("/orders/{orderId}")
  public String viewOrderDetail(@PathVariable Long orderId, Model model) {
    OrdersSearchResponseDto orderDetail = ordersService.searchOrderWithItems(orderId);
    model.addAttribute("orderDetail", orderDetail);
    return "view_orderDetail"; // 상세페이지 템플릿
  }

}
