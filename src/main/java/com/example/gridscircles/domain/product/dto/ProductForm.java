package com.example.gridscircles.domain.product.dto;

import com.example.gridscircles.domain.product.enums.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductForm {

    private String name;
    private Category category;
    private String description;
    private String price;
    private MultipartFile file;

}
