package com.goorm.baromukja.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goorm.baromukja.entity.Reservation;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/05
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReservationRequest {
    private Long restaurantId;
    // 예약자 정보
    private String username;

    // 인원
    private int numberOfReservations;

    // 예약 추가 요청 사항
    private String comment;

    // 예약 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reservationTime;

    // 예약 신청 시간
    private LocalDateTime createAt;

    public Reservation toEntity() {
        return Reservation.builder()
                .comment(this.comment)
                .reservationTime(this.reservationTime)
                .numberOfReservations(this.numberOfReservations)
                .createdAt(this.createAt)
                .build();
    }
}
