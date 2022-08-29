package com.example.extendablechattingbe.security;

import lombok.Getter;

public enum UserRole {
    USER("ROLE_USER"), // ROLE_ : Spring Security 권한 표현을 하는 문자열 규칙
    ADMIN("ROLE_ADMIN");

    @Getter
    private final String name;

    UserRole(String name) {
        this.name = name;
    }

}
