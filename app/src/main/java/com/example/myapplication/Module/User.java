package com.example.myapplication.Module;

import java.util.List;

public class User {
    String userId;
    String email;
    String password;
    List<TruyenTranh> listFavorite;
    List<TruyenTranh> listHistory;
    public User(){
    }

    public User(String userId, String email, String password, List<TruyenTranh> listFavorite, List<TruyenTranh> listHistory) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.listFavorite = listFavorite;
        this.listHistory = listHistory;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public List<TruyenTranh> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(List<TruyenTranh> listFavorite) {
        this.listFavorite = listFavorite;
    }

    public List<TruyenTranh> getListHistory() {
        return listHistory;
    }

    public void setListHistory(List<TruyenTranh> listHistory) {
        this.listHistory = listHistory;
    }
}
