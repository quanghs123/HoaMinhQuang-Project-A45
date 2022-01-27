package com.example.myapplication.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TruyenTranh implements Serializable {
    int id;
    String tenTruyen;
    String tenChap;
    String linkAnh;
    String capNhat;
    String theLoai;
    String noiDung;
    String tacGia;
    String tinhTrang;
    List<ChapTruyen> chapTruyen;
    boolean favorite;
    public TruyenTranh(){

    }

    public TruyenTranh(int id, String tenTruyen, String tenChap, String linkAnh, String capNhat, String theLoai, String noiDung, String tacGia, String tinhTrang, List<ChapTruyen> chapTruyen, boolean favorite) {
        this.id = id;
        this.tenTruyen = tenTruyen;
        this.tenChap = tenChap;
        this.linkAnh = linkAnh;
        this.capNhat = capNhat;
        this.theLoai = theLoai;
        this.noiDung = noiDung;
        this.tacGia = tacGia;
        this.tinhTrang = tinhTrang;
        this.chapTruyen = chapTruyen;
        this.favorite = favorite;
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

    public List<ChapTruyen> getChapTruyen() {
        return chapTruyen;
    }

    public void setChapTruyen(List<ChapTruyen> chapTruyen) {
        this.chapTruyen = chapTruyen;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
    
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("favorite",favorite);

        return result;
    }
}
