package com.goorm.baromukja.repository.queryDSL;

import com.goorm.baromukja.dto.location.LocationResponseWithRestaurant;
import com.goorm.baromukja.entity.Location;
import com.goorm.baromukja.entity.QLocation;
import com.goorm.baromukja.entity.QRestaurant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LocationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public LocationResponseWithRestaurant findByIdWithRestaurant(Long locationId) {
        QLocation qLocation = QLocation.location;
        QRestaurant qRestaurant = QRestaurant.restaurant;
        Location location = jpaQueryFactory
                .selectFrom(qLocation)
                .join(qLocation.restaurant, qRestaurant)
                .where(qLocation.id.eq(locationId))
                .fetchOne();
        assert location != null;
        return location.toResponseWithRestaurant();
    }
}
