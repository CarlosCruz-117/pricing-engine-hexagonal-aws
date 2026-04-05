package com.company.pricingengine.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Case price not found -> 404
     * @param ex
     * @return custom HTTP 404 mssg
     */
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleNotFound(PriceNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new CustomErrorResponse("PRICE_NOT_FOUND", ex.getMessage()));
    }

    /**
     * Case invalid params -> 400
     * @param ex Exception
     * @return custom HTTP 400 mssg
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomErrorResponse> handleTypeMismatch(Exception ex) {

        return ResponseEntity.badRequest()
                .body(new CustomErrorResponse("INVALID_PARAMETER", "Invalid parameter format"));
    }

    /**
     * Case missing params -> 400
     * @param ex Exception
     * @return custom HTTP 400 mssg
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CustomErrorResponse> handleMissingParams(Exception ex) {

        return ResponseEntity.badRequest()
                .body(new CustomErrorResponse("MISSING_PARAMETER", ex.getMessage()));
    }
}
