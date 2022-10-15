package com.goorm.baromukja.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/14
 */

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    USER("ROLE_USER")
    , ADMIN("ROLE_ADMIN");

    private final String role;
}
