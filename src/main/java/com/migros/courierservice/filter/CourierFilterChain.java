package com.migros.courierservice.filter;

import com.migros.courierservice.model.request.CourierTrackerRequest;

import java.util.ArrayList;
import java.util.List;

public class CourierFilterChain {
    private List<CourierFilter> filters = new ArrayList<>();

    public void addFilter(CourierFilter filter) {
        filters.add(filter);
    }

    public boolean apply(CourierTrackerRequest courierTracker) {
        for (CourierFilter filter : filters) {
            if (!filter.apply(courierTracker)) {
                return false;
            }
        }
        return true;
    }
}
