package com.example.agro.models;

public class UserLogin {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "email='" + username + '\'' +
                ", passworddd='" + password + '\'' +
                '}';
    }
}
