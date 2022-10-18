package com.goorm.baromukja.service.service;

import com.goorm.baromukja.dto.reservation.ReservationRequest;
import com.goorm.baromukja.dto.reservation.ReservationResponse;
import com.goorm.baromukja.dto.reservation.ReservationResponseWithUsername;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/05
 */

public interface ReservationService {
    ReservationResponse findById(Long id);

    void delete(Long reservationId);

    ReservationResponseWithUsername findByIdWithUsername(Long id);

    ReservationResponse save(ReservationRequest request);

    List<ReservationResponse> findAll();

    List<ReservationResponse> findAllByUsername(String username);

    List<ReservationResponse> findAllByRestaurantId(Long restaurantId);

    int countNumberOfReservations(Long restaurantId, LocalDateTime reservationTime);

    // List<ReservationResponse> findByMember(Long memberId);
    // List<ReservationResponse> findByRestaurant(Long restraurantId);
}
