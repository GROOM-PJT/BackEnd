package com.goorm.baromukja.baseUtil.config.auth;

import com.goorm.baromukja.entity.Member;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/*
1. 시큐리티가 login 주소 요청이 오면 가로채서 로그인을 진행한다.
2. 로그인 진행이 완료되면 session을 만들어준다. (Security ContextHolder = 시큐리티 세션 저장소)
3. 시큐리티가 갖고있는 세션에 들어갈 수 있는 Object 는 정해져있다.
4. Object == Authentication Type 객체
5. Authentication 안에 User 정보가 있어야 한다.
6. User 객체 타입 == UserDetails 타입 객체이다.

<정리>
Security Session 에 객체를 저장해준다.
-> 이곳에 들어갈 수 있는 객체는 Authentication
-> Authentication 객체는 User 객체를 저장한다.
-> User 객체는 UserDetails 객체이다.
==> 따라서 서비스는 UserDetails 를 상속받는 User 를 만들어야 한다.

UserDetails(상속:PrincipalDetails) 를 만든다.
 */

@Data
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

	private final Member member;

	// 해당 유저의 권한을 리턴한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		this.member.getRoleList().forEach(R -> {
			authorities.add(() -> R);
		});
		return authorities;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
