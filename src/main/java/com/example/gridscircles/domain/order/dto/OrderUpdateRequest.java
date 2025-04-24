package com.example.gridscircles.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderUpdateRequest {

    @NotBlank(message = "주소를 입력해주세요")
    private String address;

    @NotBlank(message = "우편번호를 입력해주세요.")
    @Pattern(regexp = "\\d{5}", message = "우편번호는 5자리 숫자여야 합니다.")
    private String zipcode;

    @Builder
    public OrderUpdateRequest(String address, String zipcode) {
        this.address = address;
        this.zipcode = zipcode;
    }
}
