package com.migros.courierservice.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CourierTracker {

    @Id
    @Column(nullable = false)
    private String transactionId;

    private long courierId;
    private Date enteredDate;
    private double latitude;
    private double longitude;

    @OneToOne
    private Store store;

}
