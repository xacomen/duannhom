/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author fuoc
 */
public class LoaiMon {
    private String MaLoaiMon;
    private String TenLoaiMon;

    public LoaiMon() {
    }

    public LoaiMon(String MaLoaiMon, String TenLoaiMon) {
        this.MaLoaiMon = MaLoaiMon;
        this.TenLoaiMon = TenLoaiMon;
    }

    public String getMaLoaiMon() {
        return MaLoaiMon;
    }

    public void setMaLoaiMon(String MaLoaiMon) {
        this.MaLoaiMon = MaLoaiMon;
    }

    public String getTenLoaiMon() {
        return TenLoaiMon;
    }

    public void setTenLoaiMon(String TenLoaiMon) {
        this.TenLoaiMon = TenLoaiMon;
    }
}
