package com.migros.courierservice.mapper;

import com.migros.courierservice.model.entity.CourierTracker;
import com.migros.courierservice.model.entity.Store;
import com.migros.courierservice.model.request.CourierTrackerRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CourierMapper {

    public CourierTracker toCourierTrackerEntity(CourierTrackerRequest courierTrackerRequest, Store store) {
        CourierTracker courierTracker = new CourierTracker();
        courierTracker.setCourierId(courierTrackerRequest.getCourierId());
        courierTracker.setLatitude(courierTrackerRequest.getLatitude());
        courierTracker.setLongitude(courierTrackerRequest.getLongitude());
        courierTracker.setEnteredDate(courierTrackerRequest.getUpdateDate());
        courierTracker.setTransactionId(UUID.randomUUID().toString());
        courierTracker.setStore(store);
        return courierTracker;
    }
}
