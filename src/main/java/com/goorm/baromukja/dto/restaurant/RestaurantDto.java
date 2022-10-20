package com.goorm.baromukja.dto.restaurant;

import com.goorm.baromukja.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/16
 */

@Getter
@Builder
public class RestaurantDto {
    private Long id;                // id

    private String name;            // 이름

    private String theme;           // 테마

    private String description;     // 설명

    public Restaurant toEntity() {
        return Restaurant.builder()
                .id(this.id)
                .build();
    }
}
