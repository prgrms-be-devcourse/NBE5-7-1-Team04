package com.example.gridscircles.global.validator;

import com.example.gridscircles.global.annotation.ProductValidFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ProductFileValidator implements ConstraintValidator<ProductValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 1024 * 1024; //1MB

    @Override
    public boolean isValid(MultipartFile multipartFile,
        ConstraintValidatorContext constraintValidatorContext) {
        return multipartFile != null && !multipartFile.isEmpty();
    }
}
