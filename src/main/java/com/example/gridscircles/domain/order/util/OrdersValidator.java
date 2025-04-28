package com.example.gridscircles.domain.order.util;

import static com.example.gridscircles.global.exception.ErrorCode.NOT_CANCELABLE_ORDER;
import static com.example.gridscircles.global.exception.ErrorCode.NOT_UPDATABLE_ORDER;

import com.example.gridscircles.domain.order.enums.OrderStatus;
import com.example.gridscircles.global.exception.ErrorException;

public class OrdersValidator {

    public static void validateCancelable(OrderStatus status) {
        if (isStatusCompletedOrCanceled(status)) {
            throw new ErrorException(NOT_CANCELABLE_ORDER);
        }
    }

    public static void validateUpdatable(OrderStatus status) {
        if (isStatusCompletedOrCanceled(status)) {
            throw new ErrorException(NOT_UPDATABLE_ORDER);
        }
    }

    private static boolean isStatusCompletedOrCanceled(OrderStatus status) {
        return status == OrderStatus.COMPLETED || status == OrderStatus.CANCELED;
    }
}
