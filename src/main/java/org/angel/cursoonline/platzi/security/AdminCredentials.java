package org.angel.cursoonline.platzi.security;

import org.springframework.stereotype.Component;

@Component
public class AdminCredentials {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password123";

    public boolean validate(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
}