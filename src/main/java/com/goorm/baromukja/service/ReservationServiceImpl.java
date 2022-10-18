package com.goorm.baromukja.service;

import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.dto.reservation.ReservationRequest;
import com.goorm.baromukja.dto.reservation.ReservationResponse;
import com.goorm.baromukja.dto.reservation.ReservationResponseWithUsername;
import com.goorm.baromukja.entity.Reservation;
import com.goorm.baromukja.repository.ReservationRepository;
import com.goorm.baromukja.repository.queryDSL.ReservatioinRepositoryCustom;
import com.goorm.baromukja.service.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/05
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservatioinRepositoryCustom reservatioinRepositoryCustom;
    private final MemberService memberService;
    private final RestaurantServiceImpl restaurantService;

    @Override
    @Transactional(readOnly = true)
    public ReservationResponse findById(Long id) {
        log.info("reservation - findById: " + id);
        return reservationRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("reservation error - " + ExMessage.RESERVATION_NONE_DATA.getMessage());
                    throw new BussinessException(ExMessage.RESERVATION_NONE_DATA.getMessage());
                }).toResponse();
    }

    @Override
    @Transactional
    public ReservationResponseWithUsername findByIdWithUsername(Long id) {
        return reservatioinRepositoryCustom.findByIdWithUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll()
                .stream().map(Reservation::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    @Transactional
    public ReservationResponse save(ReservationRequest request) {
        Reservation reservation = request.toEntity();
        reservation.setMember(memberService.findByUsername(request.getUsername()).toEntity());
        reservation.setRestaurant(restaurantService.findByIdDto(request.getRestaurantId()).toEntity());
        return reservationRepository.save(reservation).toResponse();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> findAllByUsername(String username) {
        return reservatioinRepositoryCustom.findAllByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> findAllByRestaurantId(Long restaurantId) {
        return reservatioinRepositoryCustom.findAllByRestaurantId(restaurantId);
    }
}
