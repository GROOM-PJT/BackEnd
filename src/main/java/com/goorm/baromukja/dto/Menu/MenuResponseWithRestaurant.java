package com.goorm.baromukja.dto.Menu;

import com.goorm.baromukja.dto.restaurant.RestaurantResponseDetail;
import com.goorm.baromukja.entity.Menu;
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
public class MenuResponseWithRestaurant {
    private Long id;
    private String foodName;
    private int price;
    private String imageUrl;

    private RestaurantResponseDetail restaurantResponseDetail;

    public Menu toEntity() {
        return Menu.builder()
                .id(this.id)
                .restaurant(this.restaurantResponseDetail.toEntity())
                .foodName(this.foodName)
                .price(this.price)
                .imageUrl(this.imageUrl)
                .build();
    }
}
