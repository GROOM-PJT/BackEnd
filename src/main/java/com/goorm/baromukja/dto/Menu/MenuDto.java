package com.goorm.baromukja.dto.Menu;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/16
 */

@Setter
@Getter
@Builder
public class MenuDto {
    private Long id;
    private String foodName;
    private int price;
}
