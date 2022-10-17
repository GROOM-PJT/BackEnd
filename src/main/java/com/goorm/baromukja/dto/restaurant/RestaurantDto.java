package com.goorm.baromukja.dto.restaurant;

import com.goorm.baromukja.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/16
 */

@Getter
@Builder
public class RestaurantDto {
    // 식당 정보
    private Long id;

    // 이름
    private String name;

    // 설명
    private String description;

    // 테마
    private String theme;

    public Restaurant toEntity() {
        return Restaurant.builder()
                .id(this.id)
                .build();
    }
}
