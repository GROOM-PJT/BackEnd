package com.goorm.baromukja.repository;

import com.goorm.baromukja.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/05
 */

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findById(Long id);

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    void deleteById(Long id);
}
