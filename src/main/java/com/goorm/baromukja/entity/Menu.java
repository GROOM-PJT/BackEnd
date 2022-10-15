package com.goorm.baromukja.entity;

import com.goorm.baromukja.dto.restaurant.MenuDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodName;

    private int price;

    public MenuDto toResponse() {
        return MenuDto.builder()
                .foodName(this.foodName)
                .price(this.price)
                .build();
    }
}
