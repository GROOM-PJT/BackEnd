package com.goorm.baromukja.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRequest {
	@ApiModelProperty(value = "아이디", example = "memberId1", required = true, position = 0)
	String username;
	@ApiModelProperty(value = "패스워드", example = "password123!", required = true, position = 1)
	String password;
}
