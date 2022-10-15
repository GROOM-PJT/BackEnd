package com.goorm.baromukja.repository;

import com.goorm.baromukja.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findById(Long id);

    List<Restaurant> findAll();

    Restaurant save(Restaurant restaurant);

    void deleteById(Long id);
}
