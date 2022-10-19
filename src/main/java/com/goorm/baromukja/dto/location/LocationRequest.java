package com.goorm.baromukja.dto.location;

import com.goorm.baromukja.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class LocationRequest {

    private String city;

    private String province;

    private String addressLine;

    private String road;

    public Location toEntity() {
        return Location.builder()
                .city(this.city)
                .province(this.province)
                .addressLine(this.addressLine)
                .road(this.road)
                .build();
    }
}
