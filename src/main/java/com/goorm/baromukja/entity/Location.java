package com.goorm.baromukja.entity;

import com.goorm.baromukja.dto.location.LocationDto;
import com.goorm.baromukja.dto.location.LocationResponse;
import com.goorm.baromukja.dto.location.LocationResponseWithRestaurant;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String province;
    private String road;
    private String addressLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public LocationResponse toResponse() {
        return LocationResponse.builder()
                .id(this.id)
                .city(this.city)
                .province(this.province)
                .road(this.road)
                .addressLine(this.addressLine)
                .build();
    }
    public LocationDto toDto() {
        return LocationDto.builder()
                .id(this.id)
                .city(this.city)
                .province(this.province)
                .road(this.road)
                .addressLine(this.addressLine)
                .build();
    }

    public LocationResponseWithRestaurant toResponseWithRestaurant() {
        return LocationResponseWithRestaurant.builder()
                .id(this.id)
                .city(this.city)
                .province(this.province)
                .road(this.road)
                .addressLine(this.addressLine)
                .build();
    }

}
