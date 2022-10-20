package com.goorm.baromukja.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goorm.baromukja.dto.Menu.MenuResponse;
import com.goorm.baromukja.entity.Restaurant;
import lombok.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponseDetail {
    private Long id;                // 식당 ID

    private String name;            // 이름

    private String theme;           // 테마

    private String description;     // 설명

    private String imageUrl;        // image URL

    private int availableTime;      // 예약 가능 시간

    private int availableCount;     // 예약 가능 인원

    private String holiday;         // 휴일(쉬는 요일) 단순 텍스트로

    // Location
    private String province;        // 도, 시(특별시, 광역시)
    private String city;            // 시, 군, 구
    private String detailAddress;   // 상세 주소



    // 영업시작 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime openTime;

    // 영업종료 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime closeTime;

    private List<MenuResponse> menu;

    public Restaurant toEntity() {
        return Restaurant.builder()
                .id(this.id)
                .build();
    }
}
