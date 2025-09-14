package com.fiap.tech.challenge.domain.reservation;

import com.fiap.tech.challenge.domain.reservation.dto.ReservationGetFilter;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationPostRequestDTO;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationResponseDTO;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationUpdateStatusPatchRequestDTO;
import com.fiap.tech.challenge.domain.reservation.entity.Reservation;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingStatusEnum;
import com.fiap.tech.challenge.domain.reservation.specification.ReservationSpecificationBuilder;
import com.fiap.tech.challenge.domain.reservation.usecase.ReservationCreateUseCase;
import com.fiap.tech.challenge.domain.reservation.usecase.ReservationUpdateUseCase;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurantuser.RestaurantUserServiceGateway;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.user.UserServiceGateway;
import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.base.BaseServiceGateway;
import com.fiap.tech.challenge.global.exception.EntityNotFoundException;
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
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceGateway extends BaseServiceGateway<IReservationRepository, Reservation> {

    private final IReservationRepository reservationRepository;
    private final RestaurantUserServiceGateway restaurantUserServiceGateway;
    private final UserServiceGateway userServiceGateway;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapperPresenter;

    @Autowired
    public ReservationServiceGateway(IReservationRepository reservationRepository, RestaurantUserServiceGateway restaurantUserServiceGateway, UserServiceGateway userServiceGateway, PageableBuilder pageableBuilder, ModelMapper modelMapperPresenter) {
        this.reservationRepository = reservationRepository;
        this.restaurantUserServiceGateway = restaurantUserServiceGateway;
        this.userServiceGateway = userServiceGateway;
        this.pageableBuilder = pageableBuilder;
        this.modelMapperPresenter = modelMapperPresenter;
    }

    @Transactional
    public ReservationResponseDTO create(ReservationPostRequestDTO reservationPostRequestDTO) {
        User loggedUser = AuthUserContextHolder.getAuthUser();
        Restaurant existingRestaurant = restaurantUserServiceGateway.findByRestaurantHashIdAndUser(reservationPostRequestDTO.getHashIdRestaurant(), loggedUser).getRestaurant();
        Reservation newReservation = new ReservationCreateUseCase(loggedUser, existingRestaurant, reservationPostRequestDTO).getBuiltedReservation();
        return modelMapperPresenter.map(save(newReservation), ReservationResponseDTO.class);
    }

    @Transactional
    public ReservationResponseDTO updateStatus(String hashId, ReservationUpdateStatusPatchRequestDTO reservationUpdateStatusPatchRequestDTO) {
        List<RestaurantUser> loggedRestaurantUsers = AuthUserContextHolder.getAuthUser().getRestaurantUsers();
        User existingUserReservation = userServiceGateway.findByHashId(reservationUpdateStatusPatchRequestDTO.getHashIdUser());
        Reservation existingReservation = findByHashIdAndUser(hashId, existingUserReservation);
        Reservation updatedReservation = new ReservationUpdateUseCase(loggedRestaurantUsers, existingReservation, reservationUpdateStatusPatchRequestDTO).getRebuiltedReservation();
        return modelMapperPresenter.map(save(updatedReservation), ReservationResponseDTO.class);
    }

    @Transactional
    public ReservationResponseDTO cancel(String hashId) {
        User loggedUser = AuthUserContextHolder.getAuthUser();
        Reservation existingReservation = findByHashIdAndUser(hashId, loggedUser);
        Reservation updatedReservation = new ReservationUpdateUseCase(existingReservation, ReservationBookingStatusEnum.CANCELED).getRebuiltedReservation();
        return modelMapperPresenter.map(save(updatedReservation), ReservationResponseDTO.class);
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
    public Reservation findByHashIdAndUser(String hashId, User user) {
        return reservationRepository.findByHashIdAndUser(hashId, user).orElseThrow(() -> new EntityNotFoundException(String.format("Nenhum pedido para o usuário com hash id %s foi encontrado.", hashId)));
    }

    @Override
    public Reservation findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("A reserva com o hash id %s não foi encontrada.", hashId));
    }
}