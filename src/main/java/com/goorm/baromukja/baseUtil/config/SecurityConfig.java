package com.goorm.baromukja.baseUtil.config;

import com.goorm.baromukja.baseUtil.config.filter.JwtAuthenticationFilter;
import com.goorm.baromukja.baseUtil.config.filter.JwtAuthorizationFilter;
import com.goorm.baromukja.baseUtil.config.service.JwtService;
import com.goorm.baromukja.baseUtil.exception.exceptionHandleClass.CustomAccessDeniedHandler;
import com.goorm.baromukja.baseUtil.exception.exceptionHandleClass.CustomAuthenticationEntryPoint;
import com.goorm.baromukja.baseUtil.filter.BeforeSecurityFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/04
 */

@Slf4j
@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터 체인에 등록된다.
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // Secured 애노테이션 활성화, PreAuthorize 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CorsFilter corsFilter;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final JwtService jwtService;

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers(
				// swagger
				"/v2/api-docs/**",
				"/swagger-resources/**",
				"/swagger-ui/**",
				"/webjars/**",
				"/swagger/**",

				"api/v1"
		);
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적인 리소스들에 대해서 시큐리티 적용 무시.
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new BeforeSecurityFilter(), BasicAuthenticationFilter.class);
		http.csrf().disable();
		http
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)    // 세션 사용 안함
				.and()
				.addFilter(corsFilter) // 인증(O) security Filter에 등록 / @CrossOrigin : (Cors를 허용하지만, 인증 안된 요청은 거름)
				.formLogin().disable() // Form login 안함
				.httpBasic().disable()
				.addFilter(jwtAuthenticationFilter())// 차단한 formLogin 대신 필터를 넣어준다. AuthenticationManager가 필요
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtService)) // 권한이나 인증이 필요한 곳에서 불리는 JWT 검증 필터
				.authorizeRequests()
				// TODO : 추가적인 권한 체크 시 여기서 도메인 설정
				// v1은 로그인 없이 확인 가능한 api
				.antMatchers("/api/v1/**")
				.permitAll()
				// v2의 모든 요청은 ROLE_USER 이상
				.antMatchers("/api/v2/**")
				.access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
				// v3의 모든 요청은 ROLE_ADMIN 이상
				.antMatchers("/api/v3/**")
				.access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll() // 그 외 모든 요청에 대해서 허용하라.

				.and()
				.exceptionHandling()
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.accessDeniedHandler(customAccessDeniedHandler);
	}

	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), jwtService);
		jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/member/login");
		return jwtAuthenticationFilter;
	}
}
