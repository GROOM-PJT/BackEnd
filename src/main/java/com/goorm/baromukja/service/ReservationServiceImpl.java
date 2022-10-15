package com.goorm.baromukja.service;

import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.dto.reservation.ReservationRequest;
import com.goorm.baromukja.dto.reservation.ReservationResponse;
import com.goorm.baromukja.entity.Reservation;
import com.goorm.baromukja.repository.ReservationRepository;
import com.goorm.baromukja.service.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public ReservationResponse findById(Long id) {
        log.info("reservation - findById: " + id);
        return reservationRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("reservation error - " + ExMessage.RESERVATION_NONE_DATA.getMessage());
                    throw new BussinessException(ExMessage.RESERVATION_NONE_DATA.getMessage());
                }).toResponse();
    }

    @Override
    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll()
                .stream().map(Reservation::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long reservationId) {
        // 헤더를 확인해서 요청한 사람이 해당 유저가 맞는지 확인
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public ReservationResponse save(ReservationRequest request) {
        // 유저정보 받아와서 같이 처리
        return reservationRepository.save(request.toEntity()).toResponse();
    }
}
