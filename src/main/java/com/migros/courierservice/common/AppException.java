package com.migros.courierservice.common;

public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
}
