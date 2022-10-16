package com.goorm.baromukja.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goorm.baromukja.dto.restaurant.RestaurantDto;
import com.goorm.baromukja.dto.restaurant.RestaurantResponse;
import com.goorm.baromukja.dto.restaurant.RestaurantResponseDetail;
import com.goorm.baromukja.dto.restaurant.RestaurantResponseWithMember;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private Long id;

    // 식당 이름
    private String name;

    // 테마
    private String theme;
    // 설명
    private String description;
    // 영업시작 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalDateTime openTime;
    // 영업종료 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalDateTime closeTime;
    // 예약가능 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalDateTime availableTime;
    // 예약가능 인원
    private int availableCount;
    // 쉬는 요일
    private String holiday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String imageUrl;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "restaurant")
    private List<Menu> menu;


    public RestaurantResponseDetail toResponseDetail() {
        return RestaurantResponseDetail.builder()
                .name(this.name)
                .description(this.description)
                .theme(this.theme)
                .openTime(this.openTime)
                .closeTime(this.closeTime)
                .availableTime(this.availableTime)
                .id(this.id)
                .imageUrl(this.imageUrl)
                .menu(menu.stream().map(Menu::toResponse).collect(Collectors.toList()))
                .build();
    }

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
                .name(this.name)
                .description(this.description)
                .theme(this.theme)
                .id(this.id)
                .imageUrl(this.imageUrl)
                .build();
    }
}
