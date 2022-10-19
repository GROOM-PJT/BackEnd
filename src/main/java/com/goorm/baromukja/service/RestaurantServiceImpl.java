package com.goorm.baromukja.service;

import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.dto.member.MemberResponse;
import com.goorm.baromukja.dto.restaurant.*;
import com.goorm.baromukja.entity.Restaurant;
import com.goorm.baromukja.repository.RestaurantRepository;
import com.goorm.baromukja.repository.queryDSL.RestaurantRepositoryCustom;
import com.goorm.baromukja.service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantRepositoryCustom restaurantRepositoryCustom;

    @Override
    @Transactional(readOnly = true)
    public RestaurantDto findByIdDto(Long id) {
        log.info("restaurant - findById: " + id);
        return restaurantRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("restaurant error - " + ExMessage.RESTAURANT_NONE_DATA.getMessage());
                    throw new BussinessException(ExMessage.RESTAURANT_NONE_DATA);
                }).toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantResponse findById(Long id) {
        log.info("restaurant - findById: " + id);
        return restaurantRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("restaurant error - " + ExMessage.RESTAURANT_NONE_DATA.getMessage());
                    throw new BussinessException(ExMessage.RESTAURANT_NONE_DATA);
                }).toResponse();
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantResponseWithMember findByIdWithMember(Long restaurantId) {
        log.info("restaurant - findById: " + restaurantId);
        return restaurantRepositoryCustom.findByIdWithMember(restaurantId);
    }


    @Override
    @Transactional(readOnly = true)
    public RestaurantResponseDetail findByIdDetail(Long restaurantId) {
        log.info("restaurant - findByIdDetail: " + restaurantId);
        return restaurantRepositoryCustom.findByIdWithMenu(restaurantId);
    }

    @Override
    @Transactional
    public void delete(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    @Override
    @Transactional
    public void addImageUrl(Long restaurantId, String imageUrl) {
        Restaurant restaurant = this.findByIdDto(restaurantId).toEntity();
        restaurant.setImageUrl(imageUrl);
        restaurantRepository.save(restaurant);
    }

    @Override
    @Transactional
    public RestaurantDto save(RestaurantRequest request, MemberResponse memberResponse) {
        Restaurant restaurant = request.toEntity();
        restaurant.setMember(memberResponse.toEntity());
        return restaurantRepository.save(restaurant).toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantResponse> findAll() {
        return restaurantRepository.findAll()
                .stream().map(Restaurant::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantResponse> findAllByTheme(String theme) {
        return restaurantRepository.findAllByTheme(theme)
                .stream().map(Restaurant::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantResponse> findAllByProvince(String province) {
        return restaurantRepository.findAllByProvince(province)
                .stream().map(Restaurant::toResponse)
                .collect(Collectors.toList());
    }
}

