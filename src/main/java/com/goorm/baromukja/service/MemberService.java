package com.goorm.baromukja.service;

import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.dto.member.MemberResponse;
import com.goorm.baromukja.dto.member.MemberSignupRequest;
import com.goorm.baromukja.entity.Member;
import com.goorm.baromukja.entity.MemberRole;
import com.goorm.baromukja.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final HttpSession session;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public List<MemberResponse> findAll() {
		return memberRepository.findAll()
				.stream()
				.map(Member::toResponse)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public MemberResponse findByUsername(String username) {
		return memberRepository.findByUsername(username)
				.orElseThrow(() -> new BussinessException(ExMessage.MEMBER_ERROR_NOT_FOUND))
				.toResponse();
	}

	@Transactional
	public void signUp(MemberSignupRequest memberSignupReq, MemberRole role) {
		String username = memberSignupReq.getUsername();
		String password1 = memberSignupReq.getPassword();
		String password2 = memberSignupReq.getPassword2();

		if (memberRepository.findByUsername(username).isPresent()) {
			throw new BussinessException(ExMessage.MEMBER_ERROR_DUPLICATE);
		}

		verifyUserInfo(username, password1, password2);

		try {
			memberRepository.save(
					memberSignupReq.toEntity(
							passwordEncoder.encode(memberSignupReq.getPassword()), role
					)
			);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BussinessException("??????????????? ?????????????????????.");
		}
	}

	@Transactional
	public void signIn(String username, String password) {
		Member member = memberRepository
				.findByUsername(username)
				.orElseThrow(() -> new BussinessException(ExMessage.MEMBER_ERROR_NOT_FOUND));

		if (!passwordEncoder.matches(password, member.getPassword())) {
			log.info("??????????????? ???????????? ????????????.");
			throw new BussinessException(ExMessage.MEMBER_ERROR_PASSWORD);
		}
		// TODO : JWT or Session ?????? ??????
	}

	private void verifyUserInfo(String userId, String password1, String password2) {
		verifyUserId(userId);
		verifyUserPassword(password1, password2);
	}

	private void verifyUserPassword(String password1, String password2) {
		Pattern passwordExpression = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
		if (!passwordExpression.matcher(password1).matches()) {
			throw new BussinessException("??????????????? ???????????? ??????????????? ???????????? 8??? ???????????? ???????????? ?????????.");
		} else if (!password1.equals(password2)) {
			throw new BussinessException("????????? ??????????????? ?????? ????????????.");
		}
	}

	private void verifyUserId(String userId) {
		// ????????? ???????????????, '_'??? ????????? ???????????? ????????? {??????, ??????, '_'} ????????? ???????????? 5 ~ 12??? ??????
		Pattern nameExpression = Pattern.compile("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$");
		if (!nameExpression.matcher(userId).matches()) {
			throw new BussinessException(ExMessage.MEMBER_ERROR_USER_ID_FORMAT);
		}
	}
}
