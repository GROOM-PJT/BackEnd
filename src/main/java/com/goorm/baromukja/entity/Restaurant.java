package com.goorm.baromukja.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goorm.baromukja.dto.restaurant.RestaurantDto;
import com.goorm.baromukja.dto.restaurant.RestaurantResponse;
import com.goorm.baromukja.dto.restaurant.RestaurantResponseWithMember;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String detailAddress;   // 상세 주소

    // 영업시작 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime openTime;

    // 영업종료 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime closeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public RestaurantResponseWithMember toResponseWithMember() {
        return RestaurantResponseWithMember.builder()
                .restaurantId(this.id)
                .username(this.member.getUsername())
                .build();
    }


    public RestaurantDto toDto() {
        return RestaurantDto.builder()
                .name(this.name)
                .description(this.description)
                .theme(this.theme)
                .id(this.id)
                .build();
    }

    public RestaurantResponse toResponse() {
        return RestaurantResponse.builder()
                .id(this.id)
                .name(this.name)
                .theme(this.theme)
                .description(this.description)
                .imageUrl(this.imageUrl)
                .holiday(this.holiday)

                .availableTime(this.availableTime)
                .availableCount(this.availableCount)

                .province(this.province)
                .detailAddress(this.detailAddress)

                .openTime(this.openTime)
                .closeTime(this.closeTime)
                .build();
    }
}
