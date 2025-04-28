package com.example.gridscircles.global.exception;

import lombok.Getter;

@Getter
public class AlertDetailException extends RuntimeException {

    private final ErrorStatus errorStatus;
    private final String message;
    private final String url;

    public AlertDetailException(ErrorStatus errorStatus, String message, String url) {
        this.errorStatus = errorStatus;
        this.message = message;
        this.url = url;
    }

    public AlertDetailException(ErrorStatus errorStatus, String message) {
        this.errorStatus = errorStatus;
        this.message = message;
        this.url = null;
    }
}
