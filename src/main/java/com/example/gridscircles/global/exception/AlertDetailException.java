package com.example.gridscircles.global.exception;

import lombok.Getter;

@Getter
public class AlertDetailException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;
    private final String url;

    public AlertDetailException(ErrorCode errorCode, String message, String url) {
        this.errorCode = errorCode;
        this.message = message;
        this.url = url;
    }

    public AlertDetailException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.url = null;
    }
}
