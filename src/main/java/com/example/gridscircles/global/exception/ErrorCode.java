package com.example.gridscircles.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // orders
    NOT_FOUND_ORDERS(ErrorStatus.NOT_FOUND, "ORDERS-001","존재하지 않는 주문입니다.")
    ;

    private final ErrorStatus errorStatus;
    private final String code;
    private final String message;
}
