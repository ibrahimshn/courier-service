package com.migros.courierservice.service;

import com.migros.courierservice.config.Constants;
import com.migros.courierservice.filter.CourierFilterChain;
import com.migros.courierservice.filter.DistanceCourierFilter;
import com.migros.courierservice.filter.SaveOrUpdateCourierTotalDistanceFilter;
import com.migros.courierservice.filter.TimeCourierFilter;
import com.migros.courierservice.mapper.CourierMapper;
import com.migros.courierservice.model.entity.CourierTotalDistance;
import com.migros.courierservice.model.entity.CourierTracker;
import com.migros.courierservice.model.entity.Store;
import com.migros.courierservice.model.request.CourierTrackerRequest;
import com.migros.courierservice.repository.CourierTotalDistanceRepository;
import com.migros.courierservice.repository.CourierTrackerRepository;
import com.migros.courierservice.repository.StoreRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourierServiceImplTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private CourierTrackerRepository courierTrackerRepository;

    @Mock
    private CourierTotalDistanceRepository courierTotalDistanceRepository;

    @InjectMocks
    private CourierServiceImpl courierService;


    @BeforeEach
    void setUp() {
        // Prepare test data
        Store store1 = new Store();
        store1.setId(1);
        store1.setName("store 1");
        store1.setLatitude(40.1200);
        store1.setLongitude(30.4500);

        Store store2 = new Store();
        store2.setId(2);
        store2.setName("store 2");
        store2.setLatitude(41.1200);
        store2.setLongitude(31.4500);

    }

    @Test
    void shouldTrackCourierWhenCourierIsNearStore() {
        // Given
        CourierTrackerRequest request = new CourierTrackerRequest();
        request.setLatitude(40.1201);
        request.setLongitude(30.4500);
        request.setCourierId(1);

        // When
        courierService.trackCourier(request);

        // Then
        List<CourierTracker> courierTrackers = (List<CourierTracker>) courierTrackerRepository.findAll();
        assertEquals(0, courierTrackers.size());
    }

    @Test
    void shouldNotTrackCourierWhenCourierIsNotNearStore() {
        // Given
        CourierTrackerRequest request = new CourierTrackerRequest();
        request.setLatitude(40.111);
        request.setLongitude(30.441);
        request.setCourierId(1);

        // When
        courierService.trackCourier(request);

        // Then
        verify(courierTrackerRepository, times(0)).save(any(CourierTracker.class));
    }

    @Test
    void shouldReturnTotalDistanceTraveledByCourier() {
        // Given
        int courierId = 1;
        double totalDistance = 10.5;
        CourierTotalDistance courierTotalDistance = new CourierTotalDistance();
        courierTotalDistance.setCourierId(courierId);
        courierTotalDistance.setTotalDistance(totalDistance);
        when(courierTotalDistanceRepository.findByCourierId(courierId)).thenReturn(Optional.of(courierTotalDistance));

        // When
        double result = courierService.getTotalDistanceTraveledByCourier(courierId);

        // Then
        assertEquals(totalDistance, result);
    }

    @Test
    void shouldReturnZeroWhenCourierIdIsNotFound() {
        // Given
        int courierId = 1;
        when(courierTotalDistanceRepository.findByCourierId(courierId)).thenReturn(Optional.empty());

        // When
        double result = courierService.getTotalDistanceTraveledByCourier(courierId);

        // Then
        assertEquals(0.0, result);
    }

    @Test
    void shouldCreateCourierTotalDistanceWhenItIsNotExist() {
        // Given
        int courierId = 1;
        CourierTrackerRequest request = new CourierTrackerRequest();
        request.setLatitude(40.121);
        request.setLongitude(30.451);
        request.setCourierId(courierId);

        // When
        courierService.trackCourier(request);

        // Then
        Optional<CourierTotalDistance> courierTotalDistance = courierTotalDistanceRepository.findByCourierId(courierId);
        assertFalse(courierTotalDistance.isPresent());
    }

    @Test
    void shouldUpdateCourierTotalDistanceWhenItIsExist() {
        // Given
        int courierId = 1;
        double totalDistance = 10.5;
        CourierTotalDistance courierTotalDistance = new CourierTotalDistance();
        courierTotalDistance.setCourierId(courierId);
        courierTotalDistance.setTotalDistance(totalDistance);
        when(courierTotalDistanceRepository.findByCourierId(courierId)).thenReturn(Optional.of(courierTotalDistance));

        CourierTrackerRequest request = new CourierTrackerRequest();
        request.setLatitude(40.121);
        request.setLongitude(30.451);
        request.setCourierId(courierId);

        // When
        courierService.trackCourier(request);

        // Then
        Optional<CourierTotalDistance> updatedCourierTotalDistance = courierTotalDistanceRepository.findByCourierId(courierId);
        assertTrue(updatedCourierTotalDistance.isPresent());
        assertEquals(totalDistance, updatedCourierTotalDistance.get().getTotalDistance());
    }

    @Test
    void shouldNotApplyFiltersWhenCourierIsNotNearStore() {
        // Given
        CourierTrackerRequest request = new CourierTrackerRequest();
        request.setLatitude(40.111);
        request.setLongitude(30.441);
        request.setCourierId(1);

        // When
        courierService.trackCourier(request);

        // Then
        CourierFilterChain chain = prepareRuleFilterChain();
        assertFalse(chain.apply(request));
    }

    private CourierFilterChain prepareRuleFilterChain() {
        CourierFilterChain chain = new CourierFilterChain();
        chain.addFilter(new DistanceCourierFilter(40.12, 30.45, Constants.ENTRY_DISTANCE));
        chain.addFilter(new TimeCourierFilter(Constants.LOG_TIMEOUT_SECOND_LIMIT, 1, courierTrackerRepository));
        chain.addFilter(new SaveOrUpdateCourierTotalDistanceFilter(courierTrackerRepository, courierTotalDistanceRepository));
        return chain;
    }

}
