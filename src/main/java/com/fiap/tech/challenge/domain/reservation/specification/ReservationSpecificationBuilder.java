package com.fiap.tech.challenge.domain.reservation.specification;

import com.fiap.tech.challenge.domain.reservation.dto.ReservationGetFilter;
import com.fiap.tech.challenge.domain.reservation.entity.Reservation;
import com.fiap.tech.challenge.global.search.enumerated.FetchDeletedEnum;
import com.fiap.tech.challenge.global.search.enumerated.SearchOperationEnum;
import com.fiap.tech.challenge.global.search.specification.BasicSpecificationBuilder;
import com.fiap.tech.challenge.global.search.specification.SearchCriteria;
import com.fiap.tech.challenge.global.util.ValidationUtil;

public class ReservationSpecificationBuilder extends BasicSpecificationBuilder<Reservation, ReservationSpecification, ReservationGetFilter> {

    @Override
    protected void initParams(ReservationGetFilter filter) {
        if (ValidationUtil.isNotBlank(filter.getHashIdRestaurant())) {
            where("restaurant.hashId", SearchOperationEnum.EQUAL, filter.getHashIdRestaurant());
        }

        if (ValidationUtil.isNotNull(filter.getBookingStatus())) {
            where("bookingStatus", SearchOperationEnum.EQUAL, filter.getBookingTime());
        }

        if (ValidationUtil.isNotNull(filter.getBookingTime())) {
            where("bookingTime", SearchOperationEnum.EQUAL, filter.getBookingTime());
        }

        if (ValidationUtil.isNotNull(filter.getDate())) {
            where("date", SearchOperationEnum.EQUAL, filter.getDate());
        }
    }

    @Override
    protected ReservationSpecification buildSpecification(String key, SearchOperationEnum operation, Object value) {
        return new ReservationSpecification(new SearchCriteria(key, operation, value));
    }

    @Override
    protected ReservationSpecification buildSpecification(String key, SearchOperationEnum operation, Object value, Object param) {
        return new ReservationSpecification(new SearchCriteria(key, operation, value, param));
    }

    @Override
    protected FetchDeletedEnum showDeleted() {
        return FetchDeletedEnum.FETCH_ALL;
    }
}