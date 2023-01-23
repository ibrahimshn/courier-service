package com.migros.courierservice.exception;

import com.migros.courierservice.common.AppException;

public class ReEntryBeforeLimitTimeException extends AppException {
    public ReEntryBeforeLimitTimeException(String message) {
        super(message);
    }
}
