package com.goorm.baromukja.repository.queryDSL;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

}
