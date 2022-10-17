package com.goorm.baromukja.repository.queryDSL;

import com.goorm.baromukja.dto.Menu.MenuResponseWithRestaurant;
import com.goorm.baromukja.entity.Menu;
import com.goorm.baromukja.entity.QMenu;
import com.goorm.baromukja.entity.QRestaurant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */

@Repository
@RequiredArgsConstructor
public class MenuRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public MenuResponseWithRestaurant findByIdWithRestaurant(Long menuId) {
        QMenu qMenu = QMenu.menu;
        QRestaurant qRestaurant = QRestaurant.restaurant;
        Menu menu = jpaQueryFactory
                        .selectFrom(qMenu)
                        .join(qMenu.restaurant, qRestaurant)
                        .where(qMenu.id.eq(menuId))
                        .fetchOne();

        assert menu != null;
        return menu.toResponseWithRestaurant();
    }
}
