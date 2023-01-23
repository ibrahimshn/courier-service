package com.migros.courierservice.strategy;

public class DistanceCalculator {
    private CalculateStrategy strategy;

    public DistanceCalculator(CalculateStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        return strategy.calculateDistance(lat1, lon1, lat2, lon2);
    }
}
