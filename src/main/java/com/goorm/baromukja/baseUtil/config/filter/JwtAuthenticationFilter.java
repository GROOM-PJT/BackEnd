package com.goorm.baromukja.baseUtil.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goorm.baromukja.baseUtil.config.JwtProperties;
import com.goorm.baromukja.baseUtil.config.auth.PrincipalDetails;
import com.goorm.baromukja.baseUtil.config.service.JwtService;
import com.goorm.baromukja.baseUtil.exception.ExMessage;
import com.goorm.baromukja.dto.member.LoginRequest;
import com.goorm.baromukja.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */


/*
 스프링 시큐리티에서 제공하는 UsernamePasswordAuthenticationFilter
 POST login으로 username, password 요청 시 UsernamePasswordAuthenticationFilter가 동작
*/

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // login 요청 시 로그인을 위해 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("TRY LOGIN USERNAME & PASSWORD : 인증 검증 _ JwtAuthenticationFilter.attemptAuthentication");
        ObjectMapper om = new ObjectMapper();
        try {
            LoginRequest login = om.readValue(request.getInputStream(), LoginRequest.class);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 인증이 정상적으로 완료되면 실행된다.
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해준다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("인증 완료 : successfulAuthentication");

        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();
        Member member = principal.getMember();
        String accessJwt = jwtService.createAccessToken(member.getId(), member.getUsername());
        String refreshJwt = jwtService.createRefreshToken();

        // login 성공 -> Refresh 토큰 재발급
        jwtService.setRefreshToken(member.getUsername(), refreshJwt);

        response.addHeader(JwtProperties.HEADER_PREFIX, JwtProperties.TOKEN_PREFIX + accessJwt);
        response.addHeader(JwtProperties.REFRESH_HEADER_PREFIX, JwtProperties.TOKEN_PREFIX + refreshJwt);
        setSuccessResponse(response, "로그인 성공");
        log.info("===============================인증 프로세스 완료========================================");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("인증 실패 : unsuccessfulAuthentication");
        String failReason =
                failed.getMessage().equals(ExMessage.MEMBER_ERROR_NOT_FOUND_ENG.getMessage())
                        ? ExMessage.MEMBER_ERROR_NOT_FOUND.getMessage()
                        : ExMessage.MEMBER_ERROR_PASSWORD.getMessage();

        setFailResponse(response, failReason);
        log.info("===============================인증 프로세스 완료========================================");
    }

    private void setSuccessResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1);
        jsonObject.put("message", message);

        response.getWriter().print(jsonObject);
    }

    private void setFailResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json;charset=UTF-8");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", message);

        response.getWriter().print(jsonObject);
    }
}
