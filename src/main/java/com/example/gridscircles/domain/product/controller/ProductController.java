package com.example.gridscircles.domain.product.controller;

import com.example.gridscircles.domain.product.dto.ProductForm;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.enums.Category;
import com.example.gridscircles.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public String createProduct(Model model) {

        model.addAttribute("categories", Category.values());

        return "/view_createProduct";
    }

    @PostMapping
    public String saveProduct(ProductForm productForm) {

        service.saveProduct(productForm);

        return "redirect:/product";
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id) {

        return service.findProductById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        service.deleteProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(ProductForm productForm, @PathVariable Long id) {
        return service.updateProduct(productForm, id);
    }


}
