package com.example.myapplication.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TruyenTranh implements Serializable {
//    {
//           tenTruyen: "Đào hoa bảo điển",
//           tenChap: "Chap 452",
//           linkAnh: "http://st.imageinstant.net/data/comics/133/dao-hoa-bao-dien-3241.jpg",
//           capNhat: "19 giờ trước",
//           theLoai : "Đô thị, Sắc hiệp",
//           noiDung : "Truyện chưa tóm tắt nội dung"
//           tacGia: "Đang cập nhật",
//           tinhTrang: "Đang tiến hành",
//    }


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


    public TruyenTranh(String tenTruyen, String tenChap, String linkAnh, String capNhat, String theLoai, String noiDung, String tacGia, String tinhTrang) {
        this.tenTruyen = tenTruyen;
        this.tenChap = tenChap;
        this.linkAnh = linkAnh;
        this.capNhat = capNhat;
        this.theLoai = theLoai;
        this.noiDung = noiDung;
        this.tacGia = tacGia;
        this.tinhTrang = tinhTrang;
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
