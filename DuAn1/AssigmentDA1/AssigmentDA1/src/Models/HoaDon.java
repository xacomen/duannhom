/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Date;

/**
 *
 * @author fuoc
 */
public class HoaDon {

    private String Mahd;
    private String Maban;
    private String Manv;
    private Date Ngaylap;
    private float Tongtien;
    private boolean trangthai;
    private int soluong;

    @Override
    public String toString() {
        return this.Mahd;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getMahd() {
        return Mahd;
    }

    public void setMahd(String Mahd) {
        this.Mahd = Mahd;
    }

    public String getMaban() {
        return Maban;
    }

    public void setMaban(String Maban) {
        this.Maban = Maban;
    }

    public String getManv() {
        return Manv;
    }

    public void setManv(String Manv) {
        this.Manv = Manv;
    }

    public Date getNgaylap() {
        return Ngaylap;
    }

    public void setNgaylap(Date Ngaylap) {
        this.Ngaylap = Ngaylap;
    }

    public float getTongtien() {
        return Tongtien;
    }

    public void setTongtien(float Tongtien) {
        this.Tongtien = Tongtien;
    }

    public boolean getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }


    public HoaDon() {
    }

    public HoaDon(String Mahd, String Maban, String Manv, Date Ngaylap, float Tongtien, boolean trangthai, int soluong) {
        this.Mahd = Mahd;
        this.Maban = Maban;
        this.Manv = Manv;
        this.Ngaylap = Ngaylap;
        this.Tongtien = Tongtien;
        this.trangthai = trangthai;
        this.soluong = soluong;
  
    }

}
