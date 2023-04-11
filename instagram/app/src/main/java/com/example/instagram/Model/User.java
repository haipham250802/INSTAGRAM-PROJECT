package com.example.instagram.Model;

public class User {
    private String id;
    private String username;
    private  String fullname;
    private  String ImgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public User(String id, String username, String fullname, String imgUrl) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        ImgUrl = imgUrl;
    }
}
