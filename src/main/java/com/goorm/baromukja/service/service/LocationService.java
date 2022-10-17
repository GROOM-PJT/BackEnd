package com.goorm.baromukja.service.service;

import com.goorm.baromukja.dto.location.LocationDto;
import com.goorm.baromukja.dto.location.LocationRequest;
import com.goorm.baromukja.dto.location.LocationResponseWithRestaurant;

public interface LocationService {
    LocationDto save(LocationRequest locationRequest, Long restaurantId);

    LocationResponseWithRestaurant findByIdWithRestaurant(Long locationId);
}
