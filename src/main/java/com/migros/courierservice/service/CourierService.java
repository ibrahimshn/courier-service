package com.migros.courierservice.service;

import com.migros.courierservice.model.request.CourierTrackerRequest;

public interface CourierService {

    void trackCourier(CourierTrackerRequest courierTrackerRequest);

    double getTotalDistanceTraveledByCourier(int courierId);

}
