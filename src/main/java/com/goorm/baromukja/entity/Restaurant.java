package com.goorm.baromukja.entity;

import com.goorm.baromukja.dto.restaurant.RestaurantResponse;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime openTime;
    // 영업종료 시간
    private LocalDateTime closeTime;
    // 예약가능 시간
    private LocalDateTime availableTime;
    // 예약가능 인원
    private int availableCount;
    // 쉬는 요일
    private String holiday;
    // 메뉴
    @OneToMany
    @JoinColumn
    private List<Menu> menu;

    public RestaurantResponse toResponse() {
        return RestaurantResponse.builder()
                .name(this.name)
                .description(this.description)
                .theme(this.theme)
                .openTime(this.openTime)
                .closeTime(this.closeTime)
                .availableTime(this.availableTime)
                .restaurantId(this.id)
                .build();
    }
}
