package com.goorm.baromukja.service;

import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.dto.restaurant.RestaurantRequest;
import com.goorm.baromukja.dto.restaurant.RestaurantResponse;
import com.goorm.baromukja.entity.Restaurant;
import com.goorm.baromukja.repository.RestaurantRepository;
import com.goorm.baromukja.service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantResponse findById(Long id) {
        log.info("restaurant - findById: " + id);
        return restaurantRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("restaurant error - " + ExMessage.RESTAURANT_NONE_DATA.getMessage());
                    throw new BussinessException(ExMessage.RESTAURANT_NONE_DATA);
                }).toResponse();
    }

    @Override
    public void delete(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    @Override
    public RestaurantResponse save(RestaurantRequest request) {
        return restaurantRepository.save(request.toEntity()).toResponse();
    }

    @Override
    public List<RestaurantResponse> findAll() {
        return restaurantRepository.findAll()
                .stream().map(Restaurant::toResponse)
                .collect(Collectors.toList());
    }

}

