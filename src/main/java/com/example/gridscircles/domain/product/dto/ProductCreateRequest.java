package com.example.gridscircles.domain.product.dto;

import com.example.gridscircles.domain.product.enums.Category;
import com.example.gridscircles.global.annotation.ByteSize;
import com.example.gridscircles.global.annotation.ProductValidFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotBlank(message = "상품명은 필수입니다.")
    @ByteSize(max = 255)
    private String name;

    private Category category;

    @NotBlank(message = "상품설명은 필수입니다.")
    @ByteSize(max = 255)
    private String description;

    @NotBlank(message = "가격 입력은 필수입니다.")
    @ByteSize(max = 255)
    private String price;

    @ProductValidFile
    private MultipartFile file;

    @Builder
    public ProductCreateRequest(String name, Category category, String description, String price,
        MultipartFile file) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.file = file;
    }
}
