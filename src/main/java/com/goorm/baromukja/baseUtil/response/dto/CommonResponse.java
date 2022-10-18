package com.goorm.baromukja.baseUtil.response.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/04
 */

@Getter
@Setter
public class CommonResponse {
    private int code;
    private String message;
}