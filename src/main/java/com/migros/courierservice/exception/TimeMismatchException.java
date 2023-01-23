package com.migros.courierservice.exception;

import com.migros.courierservice.common.AppException;

public class TimeMismatchException extends AppException {
    public TimeMismatchException(String message) {
        super(message);
    }
}
