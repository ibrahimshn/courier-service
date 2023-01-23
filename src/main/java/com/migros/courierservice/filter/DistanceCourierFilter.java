package com.migros.courierservice.filter;

import com.migros.courierservice.model.request.CourierTrackerRequest;
import com.migros.courierservice.strategy.DistanceCalculator;
import com.migros.courierservice.strategy.HaversineDistanceStrategy;
import com.migros.courierservice.strategy.VincentyDistanceStrategy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DistanceCourierFilter implements CourierFilter {
    private final double storeLat;
    private final double storeLon;
    private final double distance;

    private final DistanceCalculator distanceCalculator = new DistanceCalculator(new VincentyDistanceStrategy());

    @Override
    public boolean apply(CourierTrackerRequest courierTracker) {
        double courierLat = courierTracker.getLatitude();
        double courierLon = courierTracker.getLongitude();
        double distance = distanceCalculator.calculateDistance(storeLat, storeLon, courierLat, courierLon);
        return distance <= this.distance;
    }

}
