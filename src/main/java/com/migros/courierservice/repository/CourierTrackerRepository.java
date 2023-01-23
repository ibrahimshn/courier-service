package com.migros.courierservice.repository;

import com.migros.courierservice.model.entity.CourierTracker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourierTrackerRepository extends CrudRepository<CourierTracker, String> {

    CourierTracker findTopByCourierIdAndStoreIdOrderByEnteredDateDesc(long courierId, long storeId);
    Optional<CourierTracker> findTopByCourierIdOrderByEnteredDateDesc(long courierId);
}
