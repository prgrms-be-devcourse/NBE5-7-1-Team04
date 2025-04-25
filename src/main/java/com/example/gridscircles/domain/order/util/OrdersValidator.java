package com.example.gridscircles.domain.order.util;

import com.example.gridscircles.domain.order.enums.OrderStatus;
import com.example.gridscircles.domain.order.exception.OrderCancelException;

public class OrdersValidator {

    public static void validateCancelable(OrderStatus status) {
        if (status == OrderStatus.COMPLETED) {
            throw new OrderCancelException("배송이 완료된 주문은 취소할 수 없습니다.");
        }
    }
}
