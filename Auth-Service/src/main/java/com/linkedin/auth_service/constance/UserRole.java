package com.linkedin.auth_service.constance;

public enum UserRole {
    ADMIN,
    USER;

    public String withPrefix() {
        return "ROLE_" + this.name();
    }
}

