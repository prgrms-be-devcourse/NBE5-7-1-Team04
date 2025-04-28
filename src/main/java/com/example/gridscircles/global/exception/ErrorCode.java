package com.example.gridscircles.global.exception;

import static com.example.gridscircles.global.exception.ErrorStatus.BAD_REQUEST;
import static com.example.gridscircles.global.exception.ErrorStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    
    NOT_FOUND_ORDERS(NOT_FOUND, "ORDERS-001", "존재하지 않는 주문입니다."),
    NOT_CANCELABLE_ORDER(BAD_REQUEST, "ORDERS-002",
        "배송이 완료되었거나 주문이 취소된 상태는 취소할 수 없습니다."),
    NOT_UPDATABLE_ORDER(BAD_REQUEST, "ORDERS-003",
        "배송이 완료되었거나 주문이 취소된 상태는 수정할 수 없습니다."),

    NOT_SAVE_PRODUCT(BAD_REQUEST, "PRODUCTS-001", "저장에 실패했습니다."),
    NOT_FOUND_PRODUCT(NOT_FOUND, "PRODUCTS-002", "존재하지 않는 상품입니다."),
    NOT_READABLE_FILE(BAD_REQUEST, "PRODUCTS-003", "이미지 파일을 읽을 수 없습니다.")
    ;


    private final ErrorStatus errorStatus;
    private final String code;
    private final String message;
}
