package com.example.myapplication.Module;

import java.io.Serializable;
import java.util.List;

public class ChapTruyen implements Serializable {
    int id;
    String tenChap;
    List<AnhTruyen> anhTruyenList;

    public ChapTruyen(){
    }

    public ChapTruyen(int id, String tenChap, List<AnhTruyen> anhTruyenList) {
        this.id = id;
        this.tenChap = tenChap;
        this.anhTruyenList = anhTruyenList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenChap() {
        return tenChap;
    }

    public void setTenChap(String tenChap) {
        this.tenChap = tenChap;
    }

    public List<AnhTruyen> getAnhTruyenList() {
        return anhTruyenList;
    }

    public void setAnhTruyenList(List<AnhTruyen> anhTruyenList) {
        this.anhTruyenList = anhTruyenList;
    }
}
