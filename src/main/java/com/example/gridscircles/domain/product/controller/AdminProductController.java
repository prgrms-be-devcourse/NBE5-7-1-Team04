package com.example.gridscircles.domain.product.controller;

import com.example.gridscircles.domain.product.dto.ProductSearchResponseDto;
import com.example.gridscircles.domain.product.service.ProductService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminProductController {

  private final ProductService productService;

  @GetMapping("/products/list")
  public String viewlistProducts(
      @RequestParam(required = false) Long productId,
      Model model,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      RedirectAttributes redirectAttributes) {

    if (productId != null) {
      try {
        // productId 파라미터가 있으면 해당 상품만 리스트로 구성 (Paged = false)
        ProductSearchResponseDto response = productService.searchProductWithItems(productId);
        model.addAttribute("isPaged", false); // 페이징 여부
        model.addAttribute("products_list", List.of(response));

      } catch (NoSuchElementException e) {
        redirectAttributes.addFlashAttribute("errorMessage",
            "검색한 상품  " + productId + "  은 존재하지 않는 상품 ID입니다.");
        return "redirect:/admin/products/list";
      }

    } else {
      // 없으면 전체 주문 목록 (Paged = true)
      Page<ProductSearchResponseDto> responseDtoPage = productService.getAllProducts(
          PageRequest.of(page, size));

      model.addAttribute("products_list", responseDtoPage);
      model.addAttribute("currentPage", page);
      model.addAttribute("totalPages", responseDtoPage.getTotalPages());
      model.addAttribute("isPaged", true);
      model.addAttribute("pageSize", responseDtoPage.getSize());

    }

    return "admin/products";
  }

  // 해당 상품의 상세페이지
  @GetMapping("/products/{productId}")
  public String viewOrderDetail(@PathVariable Long productId, Model model) {
    ProductSearchResponseDto productDetail = productService.searchProductWithItems(productId);
    model.addAttribute("productDetail", productDetail);
    return "view_find_products"; // 상세페이지 템플릿
  }


}
