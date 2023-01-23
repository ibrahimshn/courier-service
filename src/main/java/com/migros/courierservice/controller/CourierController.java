package com.migros.courierservice.controller;

import com.migros.courierservice.model.request.CourierTrackerRequest;
import com.migros.courierservice.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("courier-api")
public class CourierController {

    private final CourierService courierService;

    @PostMapping("/track-courier")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void trackCourier(@RequestBody @Valid CourierTrackerRequest courierTrackerRequest) {
        courierService.trackCourier(courierTrackerRequest);
    }

    @GetMapping("/total-distance")
    public double getTotalDistance(@RequestParam @Min(1) int courierId) {
        return courierService.getTotalDistanceTraveledByCourier(courierId);
    }


}
