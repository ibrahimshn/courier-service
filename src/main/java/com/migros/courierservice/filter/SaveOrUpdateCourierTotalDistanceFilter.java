package com.migros.courierservice.filter;

import com.migros.courierservice.model.entity.CourierTotalDistance;
import com.migros.courierservice.model.request.CourierTrackerRequest;
import com.migros.courierservice.repository.CourierTotalDistanceRepository;
import com.migros.courierservice.repository.CourierTrackerRepository;
import com.migros.courierservice.strategy.DistanceCalculator;
import com.migros.courierservice.strategy.VincentyDistanceStrategy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaveOrUpdateCourierTotalDistanceFilter implements CourierFilter {

    private final CourierTrackerRepository courierTrackerRepository;
    private final CourierTotalDistanceRepository courierTotalDistanceRepository;

    private final DistanceCalculator distanceCalculator = new DistanceCalculator(new VincentyDistanceStrategy());

    @Override
    public boolean apply(CourierTrackerRequest courierTrackerRequest) {
        courierTrackerRepository.findTopByCourierIdOrderByEnteredDateDesc(courierTrackerRequest.getCourierId()).ifPresentOrElse( courierTracker -> {
            double distanceBetweenTwoPoints = distanceCalculator.calculateDistance(courierTrackerRequest.getLatitude(),
                    courierTrackerRequest.getLongitude(),
                    courierTracker.getLatitude(),
                    courierTracker.getLongitude());

            courierTotalDistanceRepository.findByCourierId(courierTrackerRequest.getCourierId()).ifPresent( courierTotalDistance -> {
                courierTotalDistance.setTotalDistance(courierTotalDistance.getTotalDistance() + distanceBetweenTwoPoints);
                courierTotalDistanceRepository.save(courierTotalDistance);
            });
        }, () -> courierTotalDistanceRepository.save(new CourierTotalDistance(courierTrackerRequest.getCourierId(), 0)));
        return true;
    }
}
