package com.example.demo.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;

public enum Role {
    USER,
    REVIEWER,
    ADMIN;

    public String getAuthority(){
        return "ROLE_" + this.name();
    }
}
