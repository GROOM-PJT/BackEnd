package com.goorm.baromukja.repository;

import com.goorm.baromukja.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu save(Menu menu);
}
