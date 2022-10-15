package com.goorm.baromukja.dto.member;

import com.goorm.baromukja.entity.Member;
import com.goorm.baromukja.entity.MemberRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class MemberSignupReq {
	@ApiModelProperty(required = true, value = "성함", example = "본명", position = 0)
	private String realName;
	@ApiModelProperty(required = true, value = "아이디", example = "memberId1", position = 1)
	private String username;
	@ApiModelProperty(required = true, value = "패스워드", example = "password123!", position = 2)
	private String password;
	@ApiModelProperty(required = true, value = "패스워드 확인", example = "password123!", position = 3)
	private String password2;

	public Member toEntity(String encryptedPw, MemberRole role) {
		return Member.builder()
				.username(this.username)
				.password(encryptedPw)
				.realName(this.realName)
				.role(role)
				.createDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.build();
	}
}
