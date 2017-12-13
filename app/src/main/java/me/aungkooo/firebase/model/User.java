package me.aungkooo.firebase.model;


import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class User
{
    public static final String USERS = "users";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    private String username, email, password, photoUrl;

    public User() {
    }

    // google account
    public User(String username, String email, String photoUrl) {
        this.username = username;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    // email
    public User(String username, String email, String password, String photoUrl) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.photoUrl = photoUrl;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
