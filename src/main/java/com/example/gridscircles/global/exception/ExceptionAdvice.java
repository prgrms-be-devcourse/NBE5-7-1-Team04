package com.example.gridscircles.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ErrorException.class)
    public Object handleErrorException(
        ErrorException e,
        Model model,
        HandlerMethod handlerMethod  // 현재 실행 중인 컨트롤러 메서드 정보
    ) {
        // @ResponseBody 또는 @RestController가 있는지 확인
        boolean isApiRequest = AnnotatedElementUtils.hasAnnotation(handlerMethod.getMethod(), ResponseBody.class);
        ErrorCode errorCode = e.getErrorCode();

        // 에러 로깅
        log.error(errorCode.getMessage(), e);

        // API 요청인 경우 JSON 응답
        if (isApiRequest) {
            HttpStatus httpStatus = switch (errorCode.getErrorStatus()) {
                case BAD_REQUEST -> HttpStatus.BAD_REQUEST;
                case FORBIDDEN -> HttpStatus.FORBIDDEN;
                case NOT_FOUND -> HttpStatus.NOT_FOUND;
                case CONFLICT -> HttpStatus.CONFLICT;
            };

            return ResponseEntity.status(httpStatus)
                .body(new ErrorResponse(errorCode.getCode(), errorCode.getMessage()));
        }
        // 웹 요청인 경우 HTML 응답
        else {
            model.addAttribute("message", errorCode.getMessage());
            model.addAttribute("url", e.getUrl());
            return "error/alert";
        }
    }

    @ExceptionHandler(AlertDetailException.class)
    public Object handleAlertDetailException(
        AlertDetailException e,
        Model model,
        HandlerMethod handlerMethod  // 현재 실행 중인 컨트롤러 메서드 정보
    ) {
        // @ResponseBody 또는 @RestController가 있는지 확인
        boolean isApiRequest = AnnotatedElementUtils.hasAnnotation(handlerMethod.getMethod(), ResponseBody.class);

        // 에러 로깅
        log.error(e.getMessage(), e);

        // API 요청인 경우 JSON 응답
        if (isApiRequest) {
            HttpStatus httpStatus = switch (e.getErrorStatus()) {
                case BAD_REQUEST -> HttpStatus.BAD_REQUEST;
                case FORBIDDEN -> HttpStatus.FORBIDDEN;
                case NOT_FOUND -> HttpStatus.NOT_FOUND;
                case CONFLICT -> HttpStatus.CONFLICT;
            };

            return ResponseEntity.status(httpStatus)
                .body(new ErrorResponse("ALERT-ERROR", e.getMessage()));
        }
        // 웹 요청인 경우 HTML 응답
        else {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("url", e.getUrl());
            return "error/alert";
        }
    }
}
