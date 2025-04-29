package com.example.gridscircles.global.annotation;


import com.example.gridscircles.global.validator.ByteSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ByteSizeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ByteSize {

    String message() default "바이트 길이가 허용 범위를 초과했습니다.";

    int max();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
