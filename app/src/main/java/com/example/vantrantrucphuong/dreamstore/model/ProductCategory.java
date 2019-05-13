package com.example.vantrantrucphuong.dreamstore.model;

/**
 * Created by Van Tran Truc Phuong on 3/9/2019.
 */

public class ProductCategory {
    public int idChungLoai;
    public String tenChungLoai;
    public int thuTuChungLoai;

    public ProductCategory(int idChungLoai,String tenChungLoai) {
        this.tenChungLoai = tenChungLoai;
        this.idChungLoai = idChungLoai;
    }

    public ProductCategory(String tenChungLoai) {
        this.tenChungLoai = tenChungLoai;
    }

    public ProductCategory(int idChungLoai) {
        this.idChungLoai = idChungLoai;
    }

    public String getTenChungLoai() {
        return tenChungLoai;
    }

    public void setTenChungLoai(String tenChungLoai) {
        this.tenChungLoai = tenChungLoai;
    }

    public int getIdChungLoai() {
        return idChungLoai;
    }

    public void setIdChungLoai(int idChungLoai) {
        this.idChungLoai = idChungLoai;
    }

    public int getThuTuChungLoai() {
        return thuTuChungLoai;
    }

    public void setThuTuChungLoai(int thuTuChungLoai) {
        this.thuTuChungLoai = thuTuChungLoai;
    }
}
