package com.migros.courierservice.strategy;

public interface CalculateStrategy {
    double calculateDistance(double lat1, double lon1, double lat2, double lon2);
}
