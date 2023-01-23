package com.migros.courierservice.filter;

import com.migros.courierservice.model.request.CourierTrackerRequest;

public interface CourierFilter {
    boolean apply(CourierTrackerRequest courierTracker);
}
