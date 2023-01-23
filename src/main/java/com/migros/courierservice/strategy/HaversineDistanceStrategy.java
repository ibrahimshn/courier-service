package com.migros.courierservice.strategy;

public class HaversineDistanceStrategy implements CalculateStrategy {
    @Override
    public double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {

        double lat1 = Math.toRadians(latitude1);
        double lon1 = Math.toRadians(longitude1);
        double lat2 = Math.toRadians(latitude2);
        double lon2 = Math.toRadians(longitude2);
        double dlat = lat2-lat1;
        double dlon = lon2-lon1;
        double a = Math.sin(dlat/2)*Math.sin(dlat/2)+Math.cos(lat1)*Math.cos(lat2)*Math.sin(dlon/2)*Math.sin(dlon/2);
        double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return 6371008.7714 * c;
    }
}
