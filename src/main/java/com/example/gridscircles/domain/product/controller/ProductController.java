package com.example.gridscircles.domain.product.controller;

import com.example.gridscircles.domain.product.dto.ProductListResponse;
import com.example.gridscircles.domain.product.dto.ProductResponse;
import com.example.gridscircles.domain.product.service.ProductService;
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

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String viewListProducts(
        Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {
        Page<ProductListResponse> responseDtoPage
            = productService.getAllProductsWithImage(PageRequest.of(page, size));

        model.addAttribute("products_list", responseDtoPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", responseDtoPage.getTotalPages());
        model.addAttribute("isPaged", true);
        model.addAttribute("pageSize", responseDtoPage.getSize());

        return "view_save_orders::#product-list";
    }

    @GetMapping("/{productId}")
    public String findProductById(@PathVariable Long productId, Model model) {
        ProductResponse findProduct = productService.findProductById(productId);

        model.addAttribute("productResponse", findProduct);
        model.addAttribute("isAdmin", false);

        return "admin/view_find_products";
    }
}
