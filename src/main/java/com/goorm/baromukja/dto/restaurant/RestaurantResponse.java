package com.goorm.baromukja.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goorm.baromukja.dto.member.MemberResponse;
import com.goorm.baromukja.entity.Restaurant;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Setter
@Builder
public class RestaurantResponse {
    private Long id;                // 식당 ID

    private String name;            // 이름

    private String theme;           // 테마

    private String description;     // 설명

    private String imageUrl;        // image URL

    private int availableTime;      // 예약 가능 시간

    private int availableCount;     // 예약 가능 인원

    private String holiday;         // 휴일(쉬는 요일) 단순 텍스트로

    // Location
    private String province;        // 검색 가능 주소(ㅇㅇ동)
    private String detailAddress;   // 상세 주소

    // 영업시작 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime openTime;

    // 영업종료 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime closeTime;


    public Restaurant toEntity() {
        return  Restaurant.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .theme(this.theme)
                .imageUrl(this.imageUrl)
                .availableCount(this.availableCount)
                .build();
    }
}
