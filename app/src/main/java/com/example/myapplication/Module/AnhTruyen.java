package com.example.myapplication.Module;

public class AnhTruyen {
    int id;
    String linkAnh;

    public AnhTruyen(){
    }

    public AnhTruyen(int id, String linkAnh) {
        this.id = id;
        this.linkAnh = linkAnh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }
}
