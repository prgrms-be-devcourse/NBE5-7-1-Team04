package com.example.gridscircles.domain.order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;

@Data
public class CreateOrdersRequest {

    @Email
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String zipcode;

    @NotEmpty
    private List<CreateOrdersProductDto> products;

    @Data
    public static class CreateOrdersProductDto {

        private Long id;
        private Integer quantity;
    }
}
