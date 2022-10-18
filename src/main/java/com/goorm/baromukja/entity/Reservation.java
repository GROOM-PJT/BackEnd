package com.goorm.baromukja.entity;
import com.goorm.baromukja.dto.reservation.ReservationResponse;
import com.goorm.baromukja.dto.reservation.ReservationResponseWithUsername;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/05
 */

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예약 번호

    // 예약시 추가 요청사항
    private String comment;

    // 인원
    private int numberOfReservations;

    // 예약 날짜
    private LocalDateTime reservationTime;

    // 예약 시간
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    public ReservationResponse toResponse() {
        return ReservationResponse.builder()
                .id(this.id)
                .comment(this.comment)
                .reservationTime(this.reservationTime)
                .people(this.numberOfReservations)
                .createAt(this.createdAt)
                .build();
    }

    public ReservationResponseWithUsername toResponseWithUsername() {
        return ReservationResponseWithUsername.builder()
                .id(this.id)
                .comment(this.comment)
                .reservationTime(this.reservationTime)
                .people(this.numberOfReservations)
                .createAt(this.createdAt)
                .username(this.member.getUsername())
                .build();
    }
}
