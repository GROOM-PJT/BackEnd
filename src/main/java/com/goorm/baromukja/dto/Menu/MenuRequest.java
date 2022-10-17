package com.goorm.baromukja.dto.Menu;

import com.goorm.baromukja.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MenuRequest {
    private String foodName;
    private int price;

    public Menu toEntity() {
        return Menu.builder()
                .foodName(this.foodName)
                .price(this.price)
                .build();
    }
}
