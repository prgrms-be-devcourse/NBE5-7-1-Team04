package com.example.gridscircles.domain.order.util;

import com.example.gridscircles.domain.order.enums.OrderStatus;
import com.example.gridscircles.domain.order.exception.OrderNotFoundException;

public class OrdersValidator {

    public static void validateCancelable(OrderStatus status) {
        if (status == OrderStatus.COMPLETED) {
            throw new OrderNotFoundException("배송이 완료된 주문은 취소할 수 없습니다.");
        }
    }
}
