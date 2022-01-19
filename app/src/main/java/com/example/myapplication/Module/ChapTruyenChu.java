package com.example.myapplication.Module;

public class ChapTruyenChu {
    int id;
    String tenChap;
    String contentHtml;
    public ChapTruyenChu(){
    }

    public ChapTruyenChu(int id, String tenChap, String contentHtml) {
        this.id = id;
        this.tenChap = tenChap;
        this.contentHtml = contentHtml;
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

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }
}
