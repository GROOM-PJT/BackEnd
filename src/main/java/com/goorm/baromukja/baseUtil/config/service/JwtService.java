package com.goorm.baromukja.baseUtil.config.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.goorm.baromukja.baseUtil.config.JwtProperties;
import com.goorm.baromukja.baseUtil.exception.BussinessException;
import com.goorm.baromukja.baseUtil.exception.CustomJwtException;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.baseUtil.response.dto.JwtErrorCode;
import com.goorm.baromukja.entity.Member;
import com.goorm.baromukja.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

import static com.auth0.jwt.JWT.require;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class JwtService {

	@Value("${jwt.secret}")
	private String SECRET_KEY;
	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public Member getMemberByRefreshToken(String token) {
		return memberRepository.findByRefreshToken(token)
				.orElseThrow(() -> new CustomJwtException(JwtErrorCode.JWT_REFRESH_EXPIRED.getCode()));
	}

	@Transactional
	public void setRefreshToken(String username, String refreshJwt) {
		memberRepository.findByUsername(username)
				.ifPresent(member -> member.setRefreshToken(refreshJwt));
	}

	@Transactional
	public void removeRefreshToken(String token) {
		memberRepository.findByRefreshToken(token)
				.ifPresent(member -> member.setRefreshToken(null));
	}

	@Transactional
	public void logout(HttpServletRequest request) {
		try {
			checkHeaderValid(request);
			String refreshJwtToken = request
					.getHeader(JwtProperties.REFRESH_HEADER_PREFIX)
					.replace(JwtProperties.TOKEN_PREFIX, "");
			removeRefreshToken(refreshJwtToken);
		} catch (Exception e) {
			throw new CustomJwtException(JwtErrorCode.JWT_REFRESH_NOT_VALID.name());
		}
	}

	public String createAccessToken(Long id, String username) {
		return JWT.create()
				.withSubject(JwtProperties.ACCESS_TOKEN)
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)) // 만료시간 10m
				.withClaim(JwtProperties.ID, id)
				.withClaim(JwtProperties.USERNAME, username)
				.sign(Algorithm.HMAC512(SECRET_KEY));
	}

	public String createRefreshToken() {
		return JWT.create()
				.withSubject(JwtProperties.REFRESH_TOKEN)
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(SECRET_KEY));
	}

	public void checkHeaderValid(HttpServletRequest request) {
		String accessJwt = request.getHeader(JwtProperties.HEADER_PREFIX);
		String refreshJwt = request.getHeader(JwtProperties.REFRESH_HEADER_PREFIX);

		if (accessJwt == null) {
			throw new CustomJwtException(JwtErrorCode.JWT_ACCESS_NOT_VALID.getCode());
		} else if (refreshJwt == null) {
			throw new CustomJwtException(JwtErrorCode.JWT_REFRESH_NOT_VALID.getCode());
		}
	}

	public void checkTokenValid(String token) {
		require(Algorithm.HMAC512(SECRET_KEY))
				.build()
				.verify(token);
	}

	public boolean isExpiredToken(String token) {
		try {
			require(Algorithm.HMAC512(SECRET_KEY)).build().verify(token);
		} catch (TokenExpiredException e) {
			log.info("만료 토큰");
			return true;
		}
		return false;
	}

	public boolean isNeedToUpdateRefreshToken(String token) {
		try {
			Date expiresAt = require(Algorithm.HMAC512(SECRET_KEY))
					.build()
					.verify(token)
					.getExpiresAt();

			Date current = new Date(System.currentTimeMillis());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(current);
			calendar.add(Calendar.DATE, 7);

			Date after7dayFromToday = calendar.getTime();

			// 7일 이내에 만료
			if (expiresAt.before(after7dayFromToday)) {
				log.info("리프레쉬 토큰 7일 이내 만료");
				return true;
			}
		} catch (TokenExpiredException e) {
			return true;
		}
		return false;
	}


	public String decode(final String token) {
		try {
			// 토큰 해독 객체 생성
			final JWTVerifier jwtVerifier = require(Algorithm.HMAC512(SECRET_KEY)).build();
			// 토큰 검증
			DecodedJWT decodedJWT = jwtVerifier.verify(token);
			// 토큰 payload 반환, 정상적인 토큰이라면 토큰 사용자 고유 ID, 아니라면 -1
			return decodedJWT.getClaim("username").asString();
		} catch (Exception jve) {
			log.error(jve.getMessage());
		}
		throw new BussinessException(ExMessage.JWT_ERROR_FORMAT.getMessage());
	}
}
