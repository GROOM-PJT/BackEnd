package com.goorm.baromukja.dto.location;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocationDto {
    private Long id;
    // 시,군,구
    private String city;
    // 시,도
    private String province;
    // 도로명
    private String road;
    // 상세 주소
    private String addressLine;
}
