package com.goorm.baromukja.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/18
 */

@Data
@Builder
public class ReservationResponseWithUsername {
    // 예약 번호
    private Long id;

    // 예약 날짜
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reservationTime;

    // 예약 추가 요청 사항
    private String comment;

    // 인원
    private int people;

    // 예약 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createAt;

    private String username;
}