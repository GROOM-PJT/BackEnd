package com.goorm.baromukja.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goorm.baromukja.entity.Restaurant;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.sql.Time;
import java.time.LocalTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RestaurantRequest {
    private String name;            // 이름

    private String theme;           // 테마

    private String description;     // 설명

    private int availableTime;      // 예약 가능 시간

    private int availableCount;     // 예약 가능 인원

    private String holiday;         // 휴일(쉬는 요일) 단순 텍스트로

    // Location
    @ApiModelProperty(value = "도/시", example = "서울시")
    private String province;        // 도, 시(특별시, 광역시)
    @ApiModelProperty(value = "시/군/구", example = "종로구")
    private String city;            // 시, 군, 구
    @ApiModelProperty(value = "상세 주소", example = "호호식당 1층")
    private String detailAddress;   // 상세 주소

    // 영업시작 시간
    @ApiModelProperty(value = "오픈 시간", example = "10:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime openTime;

    // 영업종료 시간
    @ApiModelProperty(value = "마감 시간", example = "20:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime closeTime;


    public Restaurant toEntity() {
        return Restaurant.builder()
                .name(this.name)
                .theme(this.theme)
                .description(this.description)
                .holiday(this.holiday)

                .availableTime(this.availableTime)
                .availableCount(this.availableCount)

                .province(this.province)
                .city(this.city)
                .detailAddress(this.detailAddress)

                .openTime(this.openTime)
                .closeTime(this.closeTime)
                .build();
    }
}
