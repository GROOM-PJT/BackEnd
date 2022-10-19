package com.goorm.baromukja.repository;

import com.goorm.baromukja.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location save(Location location);
}
