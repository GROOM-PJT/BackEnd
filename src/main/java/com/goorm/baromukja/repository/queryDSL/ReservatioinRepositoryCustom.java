package com.goorm.baromukja.repository.queryDSL;

import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.dto.reservation.ReservationResponse;
import com.goorm.baromukja.entity.QReservation;
import com.goorm.baromukja.entity.Reservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/07
 */

@Repository
@RequiredArgsConstructor
public class ReservatioinRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public ReservationResponse findByRestaurantId(Long restaurantId) {
        QReservation qReservation = QReservation.reservation;
        Reservation reservation = jpaQueryFactory
                .selectFrom(qReservation)
                .fetchOne();
        try {
            assert reservation != null;
            return reservation.toResponse();
        } catch (Exception e) {
            throw new BussinessException(ExMessage.RESERVATION_NONE_DATA);
        }
    }
}
