package com.goorm.baromukja.repository.queryDSL;

import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.dto.reservation.ReservationResponse;
import com.goorm.baromukja.dto.reservation.ReservationResponseWithUsername;
import com.goorm.baromukja.entity.QMember;
import com.goorm.baromukja.entity.QReservation;
import com.goorm.baromukja.entity.Reservation;
import com.goorm.baromukja.entity.Restaurant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/07
 */

@Repository
@RequiredArgsConstructor
public class ReservatioinRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public List<ReservationResponse> findAllByUsername(String username) {
        QReservation qReservation = QReservation.reservation;
        List<Reservation> reservationList = jpaQueryFactory
                .selectFrom(qReservation)
                .join(qReservation.member).fetchJoin()
                .where(qReservation.member.username.eq(username))
                .fetch();
        try {
            return reservationList.stream().map(Reservation::toResponse).collect(Collectors.toList());
        } catch (Exception e) {
            throw new BussinessException(ExMessage.RESERVATION_NONE_DATA);
        }
    }

    public List<ReservationResponse> findAllByRestaurantId(Long restaurantId) {
        QReservation qReservation = QReservation.reservation;
        List<Reservation> reservationList = jpaQueryFactory
                .selectFrom(qReservation)
                .join(qReservation.restaurant).fetchJoin()
                .where(qReservation.restaurant.id.eq(restaurantId))
                .fetch();
        try {
            return reservationList.stream().map(Reservation::toResponse).collect(Collectors.toList());
        } catch (Exception e) {
            throw new BussinessException(ExMessage.RESERVATION_NONE_DATA);
        }
    }

    public ReservationResponseWithUsername findByIdWithUser(Long reservationId) {
        QReservation qReservation = QReservation.reservation;
        Reservation reservation = jpaQueryFactory
                .selectFrom(qReservation)
                .join(qReservation.member)
                .where(qReservation.id.eq(reservationId))
                .fetchOne();

        assert reservation != null;
        return reservation.toResponseWithUsername();
    }

    public int countNumberOfReservations(Long restaurantId, LocalDateTime reservationTime) {
        QReservation qReservation = QReservation.reservation;
        List<Reservation> reservationList = jpaQueryFactory
                .selectFrom(qReservation)
                .where(qReservation.restaurant.id.eq(restaurantId))
                .where(qReservation.reservationTime.eq(reservationTime))
                .fetch();

        return reservationList.stream()
                .map(Reservation::getNumberOfReservations)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
