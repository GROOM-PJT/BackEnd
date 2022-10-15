package com.goorm.baromukja.baseUtil.config;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/15
 */

public interface JwtProperties {
    String ID = "id";
    String USERNAME = "username";
    String TOKEN_PREFIX = "Bearer ";
    String ACCESS_TOKEN = "AccessToken";
    String REFRESH_TOKEN = "RefreshToken";
    long EXPIRATION_TIME = (1000 * 10 * 6) * 30; // 30min
    long REFRESH_EXPIRATION_TIME = (1000 * 60 * 60 * 24) * 21; // 21days
    String HEADER_PREFIX = "Authorization";
    String REFRESH_HEADER_PREFIX = "Authorization-refresh";
    String EXCEPTION = "Exception";
}
