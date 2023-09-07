/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

/**
 *
 * @author fuoc
 */
public class HDCTviewmodel {

    private String Mahdct;
    private String Mamon;
    private String tenmon;
    private int sl;
    private float Thanhtien;
    private float tongtien;

    public HDCTviewmodel(String Mahdct, String Mamon, String tenmon, int sl, float Thanhtien, float tongtien) {
        this.Mahdct = Mahdct;
        this.Mamon = Mamon;
        this.tenmon = tenmon;
        this.sl = sl;
        this.Thanhtien = Thanhtien;
        this.tongtien = tongtien;
    }

    public HDCTviewmodel() {
    }

    public String getMahdct() {
        return Mahdct;
    }

    public void setMahdct(String Mahdct) {
        this.Mahdct = Mahdct;
    }

    public String getMamon() {
        return Mamon;
    }

    public void setMamon(String Mamon) {
        this.Mamon = Mamon;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public float getThanhtien() {
        return Thanhtien;
    }

    public void setThanhtien(float Thanhtien) {
        this.Thanhtien = Thanhtien;
    }

    public float getTongtien() {
        return tongtien;
    }

    public void setTongtien(float tongtien) {
        this.tongtien = tongtien;
    }

}
