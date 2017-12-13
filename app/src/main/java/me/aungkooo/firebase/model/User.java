package me.aungkooo.firebase.model;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class User
{
    public static final String USERS = "users";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    private String username, email, password;

    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
