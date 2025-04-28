package com.example.gridscircles.global.exception;

import lombok.Getter;

@Getter
public class AlertDetailException extends RuntimeException {

    private final ErrorStatus errorStatus;
    private final String message;
    private final String url;

    public AlertDetailException(String message, String url) {
        this.errorStatus = ErrorStatus.BAD_REQUEST;
        this.message = message;
        this.url = url;
    }

    public AlertDetailException(String message) {
        this.errorStatus = ErrorStatus.BAD_REQUEST;
        this.message = message;
        this.url = null;
    }
}
