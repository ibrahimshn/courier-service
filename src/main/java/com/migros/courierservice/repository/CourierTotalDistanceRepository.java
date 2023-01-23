package com.migros.courierservice.repository;

import com.migros.courierservice.model.entity.CourierTotalDistance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierTotalDistanceRepository extends CrudRepository<CourierTotalDistance, Long> {
    Optional<CourierTotalDistance> findByCourierId(long courierId);
}
