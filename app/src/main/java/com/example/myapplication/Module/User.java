package com.example.myapplication.Module;

import java.util.List;

public class User {
    String userId;
    String email;
    String password;
    List<TruyenTranh> listTruyenTranh;
    List<TruyenTranh> listFavorite;
    List<TruyenTranh> listHistory;
    List<TruyenChu> listTruyenChu;
    List<TruyenChu> listTruyenChuFavorite;
    List<TruyenChu> listTruyenChuHistory;


    public User(){
    }

    public User(String userId, String email, String password, List<TruyenTranh> listTruyenTranh, List<TruyenTranh> listFavorite, List<TruyenTranh> listHistory, List<TruyenChu> listTruyenChu, List<TruyenChu> listTruyenChuFavorite, List<TruyenChu> listTruyenChuHistory) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.listTruyenTranh = listTruyenTranh;
        this.listFavorite = listFavorite;
        this.listHistory = listHistory;
        this.listTruyenChu = listTruyenChu;
        this.listTruyenChuFavorite = listTruyenChuFavorite;
        this.listTruyenChuHistory = listTruyenChuHistory;
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

    public List<TruyenTranh> getListTruyenTranh() {
        return listTruyenTranh;
    }

    public void setListTruyenTranh(List<TruyenTranh> listTruyenTranh) {
        this.listTruyenTranh = listTruyenTranh;
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

    public List<TruyenChu> getListTruyenChu() {
        return listTruyenChu;
    }

    public void setListTruyenChu(List<TruyenChu> listTruyenChu) {
        this.listTruyenChu = listTruyenChu;
    }

    public List<TruyenChu> getListTruyenChuFavorite() {
        return listTruyenChuFavorite;
    }

    public void setListTruyenChuFavorite(List<TruyenChu> listTruyenChuFavorite) {
        this.listTruyenChuFavorite = listTruyenChuFavorite;
    }

    public List<TruyenChu> getListTruyenChuHistory() {
        return listTruyenChuHistory;
    }

    public void setListTruyenChuHistory(List<TruyenChu> listTruyenChuHistory) {
        this.listTruyenChuHistory = listTruyenChuHistory;
    }
}
