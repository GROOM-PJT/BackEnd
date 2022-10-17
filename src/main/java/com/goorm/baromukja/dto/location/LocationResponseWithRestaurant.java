package com.goorm.baromukja.dto.location;

import com.goorm.baromukja.dto.restaurant.RestaurantResponseDetail;
import com.goorm.baromukja.entity.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocationResponseWithRestaurant {
    private Long id;
    private String city;
    private String province;
    private String road;
    private String addressLine;

    private RestaurantResponseDetail restaurantResponseDetail;

    public Location toEntity() {
        return Location.builder()
                .id(this.id)
                .restaurant(this.restaurantResponseDetail.toEntity())
                .city(this.city)
                .province(this.province)
                .road(this.road)
                .addressLine(this.addressLine)
                .build();
    }
}
