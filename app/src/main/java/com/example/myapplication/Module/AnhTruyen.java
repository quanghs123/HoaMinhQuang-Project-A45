package com.example.myapplication.Module;

import java.io.Serializable;

public class AnhTruyen implements Serializable {
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
