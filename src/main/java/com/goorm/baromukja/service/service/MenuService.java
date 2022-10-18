package com.goorm.baromukja.service.service;

import com.goorm.baromukja.dto.Menu.MenuDto;
import com.goorm.baromukja.dto.Menu.MenuRequest;
import com.goorm.baromukja.dto.Menu.MenuResponse;
import com.goorm.baromukja.dto.Menu.MenuResponseWithRestaurant;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */

public interface MenuService {
    void addImage(String imgUrl, Long menuId);

    MenuDto save(MenuRequest menuRequest, Long restaurantId);

    MenuResponseWithRestaurant findByIdWithRestaurant(Long menuId);
}
