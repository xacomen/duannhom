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
public class HDCT {

    private String MaCTHD;
    private String MaHD_FK;
    private String MaMon_FK;
    private int SoLuong;
    private float DonGia;

    @Override
    public String toString() {
        return this.MaCTHD;
    }

    public String getMaCTHD() {
        return MaCTHD;
    }

    public void setMaCTHD(String MaCTHD) {
        this.MaCTHD = MaCTHD;
    }

    public String getMaHD_FK() {
        return MaHD_FK;
    }

    public void setMaHD_FK(String MaHD_FK) {
        this.MaHD_FK = MaHD_FK;
    }

    public String getMaMon_FK() {
        return MaMon_FK;
    }

    public void setMaMon_FK(String MaMon_FK) {
        this.MaMon_FK = MaMon_FK;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public float getDonGia() {
        return DonGia;
    }

    public void setDonGia(float DonGia) {
        this.DonGia = DonGia;
    }

    public HDCT() {
    }

    public HDCT(String MaCTHD, String MaHD_FK, String MaMon_FK, int SoLuong, float DonGia) {
        this.MaCTHD = MaCTHD;
        this.MaHD_FK = MaHD_FK;
        this.MaMon_FK = MaMon_FK;
        this.SoLuong = SoLuong;
        this.DonGia = DonGia;
    }

    

}
