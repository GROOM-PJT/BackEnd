package com.goorm.baromukja.baseUtil.config.auth;

import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.entity.Member;
import com.goorm.baromukja.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

	private final MemberRepository memberRepository;

	// 시큐리티 session -> Authentication -> UserDetails
	// 시큐리티 세션(내부 Authentication(내부 UserDetails(PrincipalDetails)))
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("LOGIN");
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(() -> new BussinessException(ExMessage.MEMBER_ERROR_NOT_FOUND_ENG));

		return new PrincipalDetails(member);
	}
}
