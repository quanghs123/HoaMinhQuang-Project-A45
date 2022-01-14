package com.example.myapplication.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TruyenTranh implements Serializable {
//    {
//        "id": 18,
//            "tenTruyen" : "Chiến Hồn Tuyệt Thế",
//            "tenChap" : "Chap 392",
//            "linkAnh" : "http://st.imageinstant.net/data/comics/231/chien-hon-tuyet-the.jpg",
//            "capNhat" : "1 tuần trước",
//            "theLoai" : "Tiên hiệp, Huyền huyễn",
//            "noiDung" : "Truyện chưa có tóm tắt",
//            "tacGia" : "Đang cập nhật",
//            "tinhTrang" : "Đang tiến hành"
//    }


    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("tenTruyen")
    @Expose
    String tenTruyen;
    @SerializedName("tenChap")
    @Expose
    String tenChap;
    @SerializedName("linkAnh")
    @Expose
    String linkAnh;
    @SerializedName("capNhat")
    @Expose
    String capNhat;
    @SerializedName("theLoai")
    @Expose
    String theLoai;
    @SerializedName("noiDung")
    @Expose
    String noiDung;
    @SerializedName("tacGia")
    @Expose
    String tacGia;
    @SerializedName("tinhTrang")
    @Expose
    String tinhTrang;

    public TruyenTranh(){

    }

    public TruyenTranh(int id, String tenTruyen, String tenChap, String linkAnh, String capNhat, String theLoai, String noiDung, String tacGia, String tinhTrang) {
        this.id = id;
        this.tenTruyen = tenTruyen;
        this.tenChap = tenChap;
        this.linkAnh = linkAnh;
        this.capNhat = capNhat;
        this.theLoai = theLoai;
        this.noiDung = noiDung;
        this.tacGia = tacGia;
        this.tinhTrang = tinhTrang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTenChap() {
        return tenChap;
    }

    public void setTenChap(String tenChap) {
        this.tenChap = tenChap;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }

    public String getCapNhat() {
        return capNhat;
    }

    public void setCapNhat(String capNhat) {
        this.capNhat = capNhat;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
