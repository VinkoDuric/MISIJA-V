package com.misijav.flipmemo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class DefaultExceptionHandler {

    private ResponseEntity<ApiError> getResponse(HttpStatus status, String message, String requestUri) {
        ApiError apiError = new ApiError(
                new Date(),
                status.value(),
                message,
                requestUri
        );

        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiError> handleException(InsufficientAuthenticationException e, HttpServletRequest request) {
        return getResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleException(AccessDeniedException e, HttpServletRequest request) {
        return getResponse(HttpStatus.FORBIDDEN, e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleException(BadCredentialsException e, HttpServletRequest request) {
        return getResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(RequestValidationException.class)
    public  ResponseEntity<ApiError> handleException(RequestValidationException e, HttpServletRequest request) {
        return getResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public  ResponseEntity<ApiError> handleException(ResourceNotFoundException e, HttpServletRequest request) {
        return getResponse(HttpStatus.NOT_FOUND, e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiError> handleException(ResourceConflictException e, HttpServletRequest request) {
        return getResponse(HttpStatus.CONFLICT, e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(RuntimeException e, HttpServletRequest request) {
        return getResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request.getRequestURI());
    }

}