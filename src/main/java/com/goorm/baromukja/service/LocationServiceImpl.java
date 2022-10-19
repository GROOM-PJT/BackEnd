package com.goorm.baromukja.service;

import com.goorm.baromukja.dto.location.LocationDto;
import com.goorm.baromukja.dto.location.LocationRequest;
import com.goorm.baromukja.dto.location.LocationResponseWithRestaurant;
import com.goorm.baromukja.entity.Location;
import com.goorm.baromukja.repository.LocationRepository;
import com.goorm.baromukja.repository.queryDSL.LocationRepositoryCustom;
import com.goorm.baromukja.service.RestaurantServiceImpl;
import com.goorm.baromukja.service.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationRepositoryCustom locationRepositoryCustom;
    private final RestaurantServiceImpl restaurantService;

    @Override
    @Transactional
    public LocationDto save(LocationRequest locationRequest, Long restaurantId) {
        Location location = locationRequest.toEntity();
        location.setRestaurant(restaurantService.findByIdDto(restaurantId).toEntity());
        return locationRepository.save(location).toDto();
    }

    @Override
    @Transactional
    public LocationResponseWithRestaurant findByIdWithRestaurant(Long locationId) {
        return locationRepositoryCustom.findByIdWithRestaurant(locationId);
    }
}
