package com.example.gridscircles.domain.product.dto;

import com.example.gridscircles.domain.product.enums.Category;
import com.example.gridscircles.global.annotation.ProductValidFile;
import com.example.gridscircles.global.validator.OnProductCreate;
import com.example.gridscircles.global.validator.OnProductUpdate;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ProductForm {

    private Long id;

    @NotBlank(message = "상품명은 필수입니다.", groups = {OnProductCreate.class, OnProductUpdate.class})
    private String name;

    private Category category;

    @NotBlank(message = "상품설명은 필수입니다.", groups = {OnProductCreate.class, OnProductUpdate.class})
    private String description;

    @NotBlank(message = "가격 입력은 필수입니다.", groups = {OnProductCreate.class, OnProductUpdate.class})
    private String price;

    @ProductValidFile(groups = OnProductCreate.class)
    private MultipartFile file;

    private String base64EncodeImage;

    private String contentType;

    @Builder
    public ProductForm(Long id, String name, Category category, String description, String price,
        MultipartFile file, String base64EncodeImage, String contentType) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.file = file;
        this.base64EncodeImage = base64EncodeImage;
        this.contentType = contentType;
    }
}
