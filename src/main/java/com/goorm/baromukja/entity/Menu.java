package com.goorm.baromukja.entity;

import com.goorm.baromukja.dto.Menu.MenuDto;
import com.goorm.baromukja.dto.Menu.MenuResponse;
import com.goorm.baromukja.dto.Menu.MenuResponseWithRestaurant;
import lombok.*;

import javax.persistence.*;

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

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public MenuResponse toResponse() {
        return MenuResponse.builder()
                .id(this.id)
                .foodName(this.foodName)
                .price(this.price)
                .imageUrl(this.imageUrl)
                .build();
    }

    public MenuDto toDto() {
        return MenuDto.builder()
                .id(this.id)
                .foodName(this.foodName)
                .price(this.price)
                .build();
    }


    public MenuResponseWithRestaurant toResponseWithRestaurant() {
        return MenuResponseWithRestaurant.builder()
                .id(this.id)
                .foodName(this.foodName)
                .price(this.price)
                .imageUrl(this.imageUrl)
                .restaurantResponseDetail(this.restaurant.toResponseDetail())
                .build();
    }
}
