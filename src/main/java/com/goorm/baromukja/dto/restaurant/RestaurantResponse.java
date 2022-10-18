package com.goorm.baromukja.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goorm.baromukja.dto.member.MemberResponse;
import com.goorm.baromukja.entity.Restaurant;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter
@Builder
public class RestaurantResponse {
    // 식당 정보
    private Long id;

    // 이름
    private String name;

    // 설명
    private String description;

    // 테마
    private String theme;

    private String imageUrl;

    private int availableCount;

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
    // 위치 정보
    // 이미지
    // 메뉴
    // private List<MenuResponse> menuResponseList;
}
