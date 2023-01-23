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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class CourierServiceImpl implements CourierService {

    private final StoreRepository storeRepository;
    private final CourierTrackerRepository courierTrackerRepository;
    private final CourierTotalDistanceRepository courierTotalDistanceRepository;
    private final CourierMapper courierMapper;

    @Transactional
    @Override
    public void trackCourier(CourierTrackerRequest courierTrackerRequest) {

        List<Store> stores = storeRepository.findAll();

        for (Store store : stores) {
            CourierFilterChain chain = prepareRuleFilterChain(store);
            if(chain.apply(courierTrackerRequest)) {
                CourierTracker courierTracker = courierMapper.toCourierTrackerEntity(courierTrackerRequest, store);
                courierTrackerRepository.save(courierTracker);
                break;
            }
        }

    }

    @Override
    public double getTotalDistanceTraveledByCourier(int courierId) {
        Optional<CourierTotalDistance> courierTotalDistance = courierTotalDistanceRepository.findByCourierId(courierId);
        return courierTotalDistance.map(CourierTotalDistance::getTotalDistance).orElse(0.0);
    }

    private CourierFilterChain prepareRuleFilterChain(Store store) {
        CourierFilterChain chain = new CourierFilterChain();
        chain.addFilter(new DistanceCourierFilter(store.getLatitude(), store.getLongitude(), Constants.ENTRY_DISTANCE));
        chain.addFilter(new TimeCourierFilter(Constants.LOG_TIMEOUT_SECOND_LIMIT, store.getId(), courierTrackerRepository));
        chain.addFilter(new SaveOrUpdateCourierTotalDistanceFilter(courierTrackerRepository, courierTotalDistanceRepository));
        return chain;
    }

}
