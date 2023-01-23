package com.migros.courierservice.filter;

import com.migros.courierservice.exception.TimeMismatchException;
import com.migros.courierservice.model.entity.CourierTracker;
import com.migros.courierservice.model.request.CourierTrackerRequest;
import com.migros.courierservice.repository.CourierTrackerRepository;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Objects;

@RequiredArgsConstructor
public class TimeCourierFilter implements CourierFilter {
    private final long timeoutLimit;
    private final long storeId;
    private final CourierTrackerRepository courierTrackerRepository;

    @Override
    public boolean apply(CourierTrackerRequest courierTracker) {
        CourierTracker lastEnteredCourierTracker = courierTrackerRepository.findTopByCourierIdAndStoreIdOrderByEnteredDateDesc(courierTracker.getCourierId(),
                storeId);
        if (Objects.isNull(lastEnteredCourierTracker)) {
            return true;
        }
        Date lastVisitDate = lastEnteredCourierTracker.getEnteredDate();
        Date currentVisitDate = courierTracker.getUpdateDate();

        if (lastVisitDate.getTime() > currentVisitDate.getTime()) {
            throw new TimeMismatchException("Update date cannot be earlier than last entered date!");
        }

        return getTimeDifferenceInSeconds(lastVisitDate, currentVisitDate) >= timeoutLimit;
    }

    public static long getTimeDifferenceInSeconds(Date date1, Date date2) {
        long diffInMillis = date2.getTime() - date1.getTime();
        return diffInMillis / 1000;
    }
}
