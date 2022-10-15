package com.goorm.baromukja.service.service;

import com.goorm.baromukja.dto.restaurant.RestaurantRequest;
import com.goorm.baromukja.dto.restaurant.RestaurantResponse;

import java.util.List;

public interface RestaurantService {
    RestaurantResponse findById(Long id);

    void delete(Long restaurantId);

    RestaurantResponse save(RestaurantRequest request);

    List<RestaurantResponse> findAll();


}
