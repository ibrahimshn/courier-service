package com.migros.courierservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourierTrackerRequest {

    @Min(1)
    private int courierId;
    @Min(-90)
    @Max(90)
    private double latitude;
    @Min(-180)
    @Max(180)
    private double longitude;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date updateDate;
}
