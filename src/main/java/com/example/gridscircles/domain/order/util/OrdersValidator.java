package com.example.gridscircles.domain.order.util;

import com.example.gridscircles.domain.order.enums.OrderStatus;
import com.example.gridscircles.domain.order.exception.OrderCancelException;
import com.example.gridscircles.domain.order.exception.OrderUpdateException;

public class OrdersValidator {

    public static void validateCancelable(OrderStatus status) {
        if (isStatusCompletedOrCanceled(status)) {
            throw new OrderCancelException("배송이 완료되거나 주문이 취소된 상태면 주문을 취소하실 수 없습니다.");
        }
    }

    public static void validateUpdateable(OrderStatus status) {
        if (isStatusCompletedOrCanceled(status)) {
            throw new OrderUpdateException("배송이 완료되거나 주문이 취소된 상태면 주문을 수정하실 수 없습니다.");
        }
    }

    private static boolean isStatusCompletedOrCanceled(OrderStatus status) {
        return status == OrderStatus.COMPLETED || status == OrderStatus.CANCELED;
    }
}
