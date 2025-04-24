package com.example.gridscircles.domain.product.controller;

import com.example.gridscircles.domain.product.dto.ProductForm;
import com.example.gridscircles.domain.product.entity.Product;
import com.example.gridscircles.domain.product.enums.Category;
import com.example.gridscircles.domain.product.service.ProductService;
import com.example.gridscircles.domain.product.util.ProductMapper;
import com.example.gridscircles.global.validator.OnProductCreate;
import com.example.gridscircles.global.validator.OnProductUpdate;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService service;
    private final Validator validator;

    @GetMapping("/new")
    public String viewProduct(Model model) {

        model.addAttribute("categories", Category.values());
        model.addAttribute("productForm", new ProductForm());
        model.addAttribute("isNew", true);

        return "view_save_products";
    }

    @PostMapping
    public String saveProduct(@Validated(OnProductCreate.class) ProductForm productForm, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){

            model.addAttribute("productForm", productForm);
            model.addAttribute("categories", Category.values());
            model.addAttribute("isNew", true);

            if (!productForm.getFile().isEmpty()){
                model.addAttribute("imageReuploadNotice", "다른 항목 오류로 인해 이미지는 다시 업로드해 주세요.");
            }

            return "view_save_products";
        }

        service.saveProduct(productForm);

        return "redirect:/admin/products/new";
    }

    @GetMapping("/{productId}")
    public String findProductById(@PathVariable Long productId, Model model) {

        Product productById = service.findProductById(productId);

        model.addAttribute("product", productById);

        model.addAttribute("base64Image", base64Encoding(productById));
        model.addAttribute("contentType", productById.getContentType());

        return "view_find_products";
    }

    @ResponseBody
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {

        service.deleteProductById(productId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("edit/{productId}")
    public String viewUpdateProduct(@PathVariable Long productId,Model model) {

        Product productById = service.findProductById(productId);

        ProductForm dto = ProductMapper.toDto(productById);
        dto.setBase64EncodeImage(base64Encoding(productById));

        model.addAttribute("categories", Category.values());
        model.addAttribute("productForm", dto);
        model.addAttribute("isNew", false);

        return "view_save_products";
    }

    @PostMapping("/{ProductId}")
    public String updateProduct(@Validated(OnProductUpdate.class) ProductForm productForm, BindingResult bindingResult, @PathVariable Long ProductId, Model model) {

        if(bindingResult.hasErrors()){
            productForm.setId(ProductId);
            model.addAttribute("productForm", productForm);
            model.addAttribute("categories", Category.values());
            model.addAttribute("isNew", false);

            if (!productForm.getFile().isEmpty()){
                model.addAttribute("imageReuploadNotice", "다른 항목 오류로 인해 이미지는 다시 업로드해 주세요.");
            }

            return "view_save_products";
        }

        service.updateProduct(productForm, ProductId);

        return "redirect:/admin/products/" + ProductId;
    }

    private static String base64Encoding(Product productById) {

        return Base64.getEncoder().encodeToString(productById.getImage());

    }

}
