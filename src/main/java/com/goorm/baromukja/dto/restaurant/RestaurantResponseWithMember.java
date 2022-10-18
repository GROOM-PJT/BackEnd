package com.goorm.baromukja.dto.restaurant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */

@Getter
@Setter
@Builder
public class RestaurantResponseWithMember {
    private Long restaurantId;
    private String username;
}
