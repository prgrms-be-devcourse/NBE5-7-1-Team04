package com.example.gridscircles.domain.product.controller;

import com.example.gridscircles.domain.product.dto.ProductCreateRequest;
import com.example.gridscircles.domain.product.dto.ProductSearchResult;
import com.example.gridscircles.domain.product.dto.ProductUpdateRequest;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.enums.Category;
import com.example.gridscircles.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @GetMapping("/new")
    public String viewCreateProduct(Model model) {
        model.addAttribute("categories", Category.values());
        model.addAttribute("productForm", new ProductCreateRequest());
        model.addAttribute("isNew", true);

        return "admin/view_save_products";
    }

    @PostMapping
    public String saveProduct(
        @Validated @ModelAttribute("productForm") ProductCreateRequest productCreateRequest,
        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", Category.values());
            model.addAttribute("isNew", true);

            if (!productCreateRequest.getFile().isEmpty()) {
                model.addAttribute("imageReUploadNotice", "다른 항목 오류로 인해 이미지는 다시 업로드해 주세요.");
            }
            return "admin/view_save_products";
        }
        Product product = productService.saveProduct(productCreateRequest);

        return "redirect:/admin/products/" + product.getId();
    }


    @GetMapping("/{productId}")
    public String viewProduct(@PathVariable Long productId, Model model) {
        model.addAttribute("productResponse", productService.findProductById(productId));
        model.addAttribute("isAdmin", true);

        return "admin/view_find_products";
    }

    @ResponseBody
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("edit/{productId}")
    public String viewUpdateProduct(@PathVariable Long productId, Model model) {
        model.addAttribute("categories", Category.values());
        model.addAttribute("productForm", productService.findProductById(productId));
        model.addAttribute("isNew", false);

        return "admin/view_save_products";
    }

    @PostMapping("/{ProductId}")
    public String updateProduct(
        @Validated @ModelAttribute("productForm") ProductUpdateRequest productUpdateRequest,
        BindingResult bindingResult, @PathVariable Long ProductId, Model model) {

        if (bindingResult.hasErrors()) {
            productUpdateRequest.setId(ProductId);

            model.addAttribute("categories", Category.values());
            model.addAttribute("isNew", false);

            if (!productUpdateRequest.getFile().isEmpty()) {
                model.addAttribute("imageReUploadNotice", "다른 항목 오류로 인해 이미지는 다시 업로드해 주세요.");
            }
            return "admin/view_save_products";
        }
        productService.updateProduct(productUpdateRequest);
        return "redirect:/admin/products/" + ProductId;
    }

    // 전체 상품 조회
    @GetMapping("/list")
    public String viewProducts(
        Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size
    ) {

        ProductSearchResult resultAll = productService.readAllProducts(PageRequest.of(page, size));
        model.addAttribute("products_list", resultAll.getProductsList());
        model.addAttribute("hasData", resultAll.isHasData());
        model.addAttribute("currentPage", resultAll.getCurrentPage());
        model.addAttribute("totalPages", resultAll.getTotalPages());
        model.addAttribute("pageSize", size);

        return "admin/view_products";
    }

    // 상품명으로 검색
    @GetMapping("/search")
    public String searchProducts(
        @RequestParam("product") String productName,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model
    ) {
        ProductSearchResult resultSearch = productService.searchProductsByName(productName,
            PageRequest.of(page, size));

        model.addAttribute("products_list", resultSearch.getProductsList());
        model.addAttribute("hasData", resultSearch.isHasData());
        model.addAttribute("currentPage", resultSearch.getCurrentPage());
        model.addAttribute("totalPages", resultSearch.getTotalPages());
        model.addAttribute("pageSize", size);
        return "admin/view_products";
    }
}
