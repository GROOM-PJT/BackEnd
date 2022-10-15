package com.goorm.baromukja.dto.restaurant;

import com.goorm.baromukja.entity.Restaurant;
import com.goorm.baromukja.entity.Menu;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RestaurantRequest {
    // 이름
    private String name;
    // 테마
    private String theme;
    // 예약 가능 시간
    private LocalDateTime availableTime;
    // 섦명
    private String description;
    // 시작 시간
    private LocalDateTime openTime;
    // 종료 시간
    private LocalDateTime closeTime;
    //메뉴
    private List<Menu> menu;

    public Restaurant toEntity() {
        return Restaurant.builder()
                .name(this.name)
                .availableTime(this.availableTime)
                .theme(this.theme)
                .openTime(this.openTime)
                .closeTime(this.closeTime)
                .description(this.description)
                .build();
    }
}
