package com.goorm.baromukja.dto.location;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocationResponse {
    private Long id;
    private String city;
    private String province;
    private String road;
    private String addressLine;
}
