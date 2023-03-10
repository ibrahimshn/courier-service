package com.migros.courierservice.exceptionhandler;

import com.migros.courierservice.common.AppException;
import com.migros.courierservice.model.error.AppError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class CommonExceptionHandler {
    @ExceptionHandler(value = AppException.class)
    public <T> T handleAppException(AppException e) {
        AppError appError = createApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return (T) new ResponseEntity<>(appError, appError.getHttpStatus());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public <T> T handleNotSupportedMethodException(HttpRequestMethodNotSupportedException e) {
        AppError appError = createApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return (T) new ResponseEntity<>(appError, appError.getHttpStatus());
    }

    @ExceptionHandler(value = Exception.class)
    public <T> T handleBaseException(Exception e) {
        AppError apiError = createApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An Error Occurred!");
        log.error("An Error Occurred:{}", e);
        return (T) new ResponseEntity<Object>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public <T> T handleMissingServletException(MissingServletRequestParameterException e) {
        AppError apiError = createApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return (T) new ResponseEntity<Object>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public <T> T handleConstraintViolationException(ConstraintViolationException e) {
        AppError apiError = createApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return (T) new ResponseEntity<Object>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public <T> T handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        AppError apiError = createApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return (T) new ResponseEntity<Object>(apiError, apiError.getHttpStatus());
    }

    private AppError createApiError(HttpStatus httpStatus, String message) {
        AppError appError = new AppError();
        appError.setError(httpStatus.getReasonPhrase());
        appError.setMessage(message);
        appError.setHttpStatus(httpStatus);
        appError.setStatus(httpStatus.value());
        return appError;
    }
}
