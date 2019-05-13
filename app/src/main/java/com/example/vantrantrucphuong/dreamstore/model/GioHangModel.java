package com.example.vantrantrucphuong.dreamstore.model;

import java.io.Serializable;

/**
 * Created by Van Tran Truc Phuong on 4/10/2019.
 */


//Tạo mảng có dữ liệu phù hợp với model
//    Tránh mất dữ liệu trong mảng khi tiếp tục mua hàng, sẽ tạo mảng toàn cục trong main activity
public class GioHangModel implements Serializable {
    public int idSP;
    public String tenSP;
    public int giaSP;
    public  String hinhSP;
    public int soLuong;
    public int size;

    public GioHangModel(int idSP, String tenSP, int giaSP, String hinhSP) {
        this.idSP = idSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.hinhSP = hinhSP;
    }

    public GioHangModel(int idSP, String tenSP, int giaSP, String hinhSP, int soLuong, int size) {
        this.idSP = idSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.hinhSP = hinhSP;
        this.soLuong = soLuong;
        this.size = size;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public String getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {
        this.hinhSP = hinhSP;
    }
}


