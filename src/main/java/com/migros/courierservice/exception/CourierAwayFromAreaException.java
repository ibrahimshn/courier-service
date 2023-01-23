package com.migros.courierservice.exception;

import com.migros.courierservice.common.AppException;

public class CourierAwayFromAreaException extends AppException {
    public CourierAwayFromAreaException(String message) {
        super(message);
    }
}
