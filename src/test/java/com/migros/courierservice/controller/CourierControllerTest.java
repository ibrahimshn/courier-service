package com.migros.courierservice.controller;

import com.migros.courierservice.model.request.CourierTrackerRequest;
import com.migros.courierservice.service.CourierService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CourierControllerTest {

    @Mock
    private CourierService courierService;

    @InjectMocks
    private CourierController courierController;

    @Test
    void shouldCallTrackCourierMethodWhenTrackCourierApiIsCalled() {
        // given
        CourierTrackerRequest request = new CourierTrackerRequest();

        // when
        courierController.trackCourier(request);

        // then
        verify(courierService).trackCourier(request);
    }

    @Test
    void shouldCallGetTotalDistanceTraveledByCourierMethodWhenTotalDistanceApiIsCalled() {
        // given
        int courierId = 1;

        // when
        courierController.getTotalDistance(courierId);

        // then
        verify(courierService).getTotalDistanceTraveledByCourier(courierId);
    }

}
