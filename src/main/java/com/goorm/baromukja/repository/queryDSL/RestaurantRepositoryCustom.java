package com.goorm.baromukja.repository.queryDSL;

import com.goorm.baromukja.dto.restaurant.RestaurantResponseDetail;
import com.goorm.baromukja.dto.restaurant.RestaurantResponseWithMember;
import com.goorm.baromukja.entity.QMenu;
import com.goorm.baromukja.entity.QRestaurant;
import com.goorm.baromukja.entity.Restaurant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public RestaurantResponseWithMember findByIdWithMember(Long restaurantId) {
        QRestaurant qRestaurant = QRestaurant.restaurant;
        Restaurant restaurant = jpaQueryFactory
                .selectFrom(qRestaurant)
                .join(qRestaurant.member)
                .where(qRestaurant.id.eq(restaurantId))
                .fetchOne();

        assert restaurant != null;
        return restaurant.toResponseWithMember();
    }

    public RestaurantResponseDetail findByIdWithMenu(Long restaurantId) {
        QRestaurant qRestaurant = QRestaurant.restaurant;
        Restaurant restaurant = jpaQueryFactory
                .selectFrom(qRestaurant)
                .join(qRestaurant.menu).fetchJoin()
                .where(qRestaurant.id.eq(restaurantId))
                .fetchOne();

        assert restaurant != null;
        return restaurant.toResponseDetail();
    }

    public Restaurant findByIdForImage(Long restuarantId) {
        QRestaurant qRestaurant = QRestaurant.restaurant;
        return jpaQueryFactory
                .selectFrom(qRestaurant)
                .join(qRestaurant.member)
                .where(qRestaurant.id.eq(restuarantId))
                .fetchOne();
    }
}
