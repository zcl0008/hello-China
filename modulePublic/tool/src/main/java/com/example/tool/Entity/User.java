package com.example.tool.Entity;

public class User {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String photoUrl;

    private String token;

    public User(String name, String email, String password, String phone, String photoUrl, String token) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.photoUrl = photoUrl;
        this.token = token;
    }

    public User(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
