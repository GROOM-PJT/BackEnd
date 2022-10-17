package com.goorm.baromukja.dto.restaurant;

import com.goorm.baromukja.dto.Menu.MenuResponse;
import com.goorm.baromukja.entity.Restaurant;
import lombok.*;

import java.time.LocalDateTime;
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
    // 식당 정보
    private Long id;

    //시작 시간
    private LocalDateTime openTime;

    //종료 시간
    private LocalDateTime closeTime;

    //예약 가능 시간
    private LocalDateTime availableTime;

    // 이름
    private String name;

    // 설명
    private String description;

    // 테마
    private String theme;

    private String imageUrl;

    private List<MenuResponse> menu;

    public Restaurant toEntity() {
        return Restaurant.builder()
                .id(this.id)
                .build();
    }
}
