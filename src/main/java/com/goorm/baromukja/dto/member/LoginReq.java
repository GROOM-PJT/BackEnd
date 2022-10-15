package com.goorm.baromukja.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginReq {
	@ApiModelProperty(value = "아이디", required = true, position = 0)
	String username;
	@ApiModelProperty(value = "패스워드", required = true, position = 1)
	String password;
}
