package com.goorm.baromukja.service;

import com.goorm.baromukja.dto.Menu.MenuDto;
import com.goorm.baromukja.dto.Menu.MenuRequest;
import com.goorm.baromukja.dto.Menu.MenuResponse;
import com.goorm.baromukja.dto.Menu.MenuResponseWithRestaurant;
import com.goorm.baromukja.entity.Menu;
import com.goorm.baromukja.repository.MenuRepository;
import com.goorm.baromukja.repository.queryDSL.MenuRepositoryCustom;
import com.goorm.baromukja.service.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuRepositoryCustom menuRepositoryCustom;
    private final RestaurantServiceImpl restaurantService;

    @Override
    @Transactional
    public void addImage(String imgUrl, Long menuId) {
        MenuResponseWithRestaurant menuResponseWithRestaurant = this.findByIdWithRestaurant(menuId);
        menuResponseWithRestaurant.setImageUrl(imgUrl);
        menuRepository.save(menuResponseWithRestaurant.toEntity());
    }

    @Override
    @Transactional
    public MenuDto save(MenuRequest menuRequest, Long restaurantId) {
        Menu menu = menuRequest.toEntity();
        menu.setRestaurant(restaurantService.findByIdDto(restaurantId).toEntity());
        return menuRepository.save(menu).toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public MenuResponseWithRestaurant findByIdWithRestaurant(Long menuId) {
        return menuRepositoryCustom.findByIdWithRestaurant(menuId);
    }
}
