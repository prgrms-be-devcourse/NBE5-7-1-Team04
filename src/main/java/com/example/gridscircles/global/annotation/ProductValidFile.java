package com.example.gridscircles.global.annotation;

import com.example.gridscircles.global.validator.ProductFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProductFileValidator.class)
public @interface ProductValidFile {

    String message() default "파일을 선택해주세요.";

    long maxSize() default 1048576;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
