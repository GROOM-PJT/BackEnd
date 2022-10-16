package com.goorm.baromukja.dto.member;

import com.goorm.baromukja.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberResponse {
	private Long id;
	private String username;
	private String role;
	private String realName;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;

    public Member toEntity() {
		return Member.builder()
				.id(this.id)
				.username(this.username)
				.build();
    }
}
