package com.example.sweater.domain;

import org.springframework.security.core.GrantedAuthority;

//не будет храниться в БД
public enum Role implements GrantedAuthority { // GrantedAuthority Представляет полномочия, предоставленные объекту аутентификации.
    ROLE;

    @Override
    public String getAuthority() {
        return name();  // name() строкове представление ROLE
    }
}
