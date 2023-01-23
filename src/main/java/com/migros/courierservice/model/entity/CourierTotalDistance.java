package com.migros.courierservice.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CourierTotalDistance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long courierId;
    private double totalDistance;

    public CourierTotalDistance(long courierId, double totalDistance) {
        this.courierId = courierId;
        this.totalDistance = totalDistance;
    }
}
