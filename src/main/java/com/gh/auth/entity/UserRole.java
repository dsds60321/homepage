package com.gh.auth.entity;

public enum UserRole {
    ROLE_USER("USER"),
    ROLE_GUEST("GUEST");

    // "USER", "ADMIN"
    private String value;

    // Constructor
    UserRole(String value) {
        this.value = value;
    }

    // GetValue
    public String getValue() {
        return this.value;
    }
}
