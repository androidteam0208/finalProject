package com.example.vantrantrucphuong.dreamstore.model;

import java.io.Serializable;

//Truyền object qua cho màn hình khác

/**
 * Created by Van Tran Truc Phuong on 3/19/2019.
 */

public class SanPham implements Serializable {
    int idCL;
    int idSP;
    int giaSP;
    String tenSP;
    String urlHinh;

    public SanPham(String urlHinh, String tenSP, int giaSP, int idSP, int idCL) {
        this.urlHinh = urlHinh;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.idSP = idSP;
        this.idCL = idCL;
    }

    public String getUrlHinh() {
        return urlHinh;
    }

    public void setUrlHinh(String urlHinh) {
        this.urlHinh = urlHinh;
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

    public int getIdCL() {
        return idCL;
    }

    public void setIdCL(int idCL) {
        this.idCL = idCL;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }
}
