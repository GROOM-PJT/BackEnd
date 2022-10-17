package com.goorm.baromukja.service.service;

import com.goorm.baromukja.dto.member.MemberResponse;
import com.goorm.baromukja.dto.restaurant.*;

import java.util.List;

public interface RestaurantService {
    RestaurantResponseDetail findByIdDetail(Long restaurantId);

    RestaurantResponse findById(Long restauraintId);

    RestaurantDto findByIdDto(Long restaurantId);

    RestaurantResponseWithMember findByIdWithMember(Long restaurantId);

    void delete(Long restaurantId);

    RestaurantDto save(RestaurantRequest request, MemberResponse memberResponse);

    void addImageUrl(Long restaurantId, String imageUrl);

    List<RestaurantResponse> findAll();


}
