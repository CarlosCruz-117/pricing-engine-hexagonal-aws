package com.company.pricingengine.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Case price not found -> 404
     * @return custom HTTP 404 mssg
     */
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleNotFound(PriceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CustomErrorResponse.builder()
                        .code("PRICE_NOT_FOUND")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    /**
     * Case invalid params -> 400
     * @return custom HTTP 400 mssg
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidParam() {
        return ResponseEntity.badRequest()
                .body(CustomErrorResponse.builder()
                        .code("INVALID_PARAMETER")
                        .message("Invalid parameter format")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    /**
     * Case missing params -> 400
     * @return custom HTTP 400 mssg
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CustomErrorResponse> handleMissingParam(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest()
                .body(CustomErrorResponse.builder()
                        .code("MISSING_PARAMETER")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
