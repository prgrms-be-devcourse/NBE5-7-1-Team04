package com.example.gridscircles.domain.product.controller;

import com.example.gridscircles.domain.product.dto.ProductCreateRequest;
import com.example.gridscircles.domain.product.dto.ProductUpdateRequest;
import com.example.gridscircles.domain.product.dto.ProductSearchResponse;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.enums.Category;
import com.example.gridscircles.domain.product.service.ProductService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String saveProduct(@Validated @ModelAttribute("productForm") ProductCreateRequest productCreateRequest, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){

            model.addAttribute("categories", Category.values());
            model.addAttribute("isNew", true);

            if (!productCreateRequest.getFile().isEmpty()){
                model.addAttribute("imageReuploadNotice", "다른 항목 오류로 인해 이미지는 다시 업로드해 주세요.");
            }

            return "admin/view_save_products";
        }

        Product product = productService.saveProduct(productCreateRequest);

        return "redirect:/admin/products/"+product.getId();
    }


    @GetMapping("/{productId}")
    public String findProductById(@PathVariable Long productId, Model model) {

        model.addAttribute("productResponse", productService.findProductById(productId));
        model.addAttribute("isAdmin", true);

        return "view_find_products";
    }

    @ResponseBody
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {

        productService.deleteProductById(productId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("edit/{productId}")
    public String viewUpdateProduct(@PathVariable Long productId,Model model) {

        model.addAttribute("categories", Category.values());
        model.addAttribute("productForm", productService.findProductById(productId));
        model.addAttribute("isNew", false);

        return "admin/view_save_products";
    }

    @PostMapping("/{ProductId}")
    public String updateProduct(@Validated @ModelAttribute("productForm") ProductUpdateRequest productUpdateRequest, BindingResult bindingResult, @PathVariable Long ProductId, Model model) {

        if(bindingResult.hasErrors()){
            productUpdateRequest.setId(ProductId);

            model.addAttribute("categories", Category.values());
            model.addAttribute("isNew", false);

            if (!productUpdateRequest.getFile().isEmpty()){
                model.addAttribute("imageReuploadNotice", "다른 항목 오류로 인해 이미지는 다시 업로드해 주세요.");
            }

            return "admin/view_save_products";
        }

        productService.updateProduct(productUpdateRequest);

        return "redirect:/admin/products/" + ProductId;
    }

    @GetMapping("/list")
    public String viewlistProducts(
        @RequestParam(required = false) String productName,
        Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        RedirectAttributes redirectAttributes) {

        if (productName != null && !productName.trim().isEmpty()) {
            try {
                // productName 파라미터가 있으면 해당 상품만 리스트로 구성
                Page<Product> responseName = productService.readProductByName(productName, PageRequest.of(page, size));
                model.addAttribute("products_list", responseName);
                model.addAttribute("hasData", responseName.hasContent());
                model.addAttribute("currentPage", responseName.getNumber());
                model.addAttribute("totalPages", responseName.getTotalPages());
                model.addAttribute("pageSize", size);

            } catch (NoSuchElementException e) {
                redirectAttributes.addFlashAttribute("errorMessage",
                    "검색한 상품  " + productName + "  은 존재하지 않는 상품입니다.");
                return "redirect:/admin/products/list";
            }

        } else {
            // 없으면 전체 주문 목록
            Page<ProductSearchResponse> responseList = productService.readAllProducts(PageRequest.of(page, size));
            System.out.println("productName   "+productName+"    findAll    ");
            model.addAttribute("products_list", responseList);
            model.addAttribute("hasData", responseList.hasContent());
            model.addAttribute("currentPage", responseList.getNumber());
            model.addAttribute("totalPages", responseList.getTotalPages());
            model.addAttribute("pageSize", size);

        }

        return "admin/view_products";
    }




}
