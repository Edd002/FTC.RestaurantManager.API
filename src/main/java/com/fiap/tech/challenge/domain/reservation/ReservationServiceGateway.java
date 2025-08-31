package com.fiap.tech.challenge.domain.reservation;

import com.fiap.tech.challenge.domain.reservation.dto.ReservationGetFilter;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationPostRequestDTO;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationPutRequestDTO;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationResponseDTO;
import com.fiap.tech.challenge.domain.reservation.entity.Reservation;
import com.fiap.tech.challenge.domain.reservation.specification.ReservationSpecificationBuilder;
import com.fiap.tech.challenge.domain.restaurant.RestaurantServiceGateway;
import com.fiap.tech.challenge.domain.restaurantuser.RestaurantUserServiceGateway;
import com.fiap.tech.challenge.global.base.BaseServiceGateway;
import com.fiap.tech.challenge.global.search.builder.PageableBuilder;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ReservationServiceGateway extends BaseServiceGateway<IReservationRepository, Reservation> {

    private final IReservationRepository reservationRepository;
    private final RestaurantServiceGateway restaurantServiceGateway;
    private final RestaurantUserServiceGateway restaurantUserServiceGateway;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapperPresenter;

    @Autowired
    public ReservationServiceGateway(IReservationRepository reservationRepository, RestaurantServiceGateway restaurantServiceGateway, RestaurantUserServiceGateway restaurantUserServiceGateway, PageableBuilder pageableBuilder, ModelMapper modelMapperPresenter) {
        this.reservationRepository = reservationRepository;
        this.restaurantServiceGateway = restaurantServiceGateway;
        this.restaurantUserServiceGateway = restaurantUserServiceGateway;
        this.pageableBuilder = pageableBuilder;
        this.modelMapperPresenter = modelMapperPresenter;
    }

    @Transactional
    public ReservationResponseDTO create(ReservationPostRequestDTO reservationPostRequestDTO) {
        return null;
    }

    @Transactional
    public ReservationResponseDTO update(String hashId, ReservationPutRequestDTO reservationPutRequestDTO) {
        return null;
    }

    @Transactional
    public Page<ReservationResponseDTO> find(ReservationGetFilter filter) {
        Pageable pageable = pageableBuilder.build(filter);
        Optional<Specification<Reservation>> specification = new ReservationSpecificationBuilder().build(filter);
        return specification
                .map(spec -> findAll(spec, pageable))
                .orElseGet(() -> new PageImpl<>(new ArrayList<>()))
                .map(reservation -> modelMapperPresenter.map(reservation, ReservationResponseDTO.class));
    }

    @Transactional
    public ReservationResponseDTO find(String hashId) {
        return modelMapperPresenter.map(findByHashId(hashId), ReservationResponseDTO.class);
    }

    @Transactional
    public void delete(String hashId) {
    }

    @Override
    public Reservation findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("A reserva com o hash id %s não foi encontrada.", hashId));
    }
}