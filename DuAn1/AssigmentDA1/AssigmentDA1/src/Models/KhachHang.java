/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;


/**
 *
 * @author DELL
 */
public class KhachHang {

    private String maKh;
    private String tenKh;
    private String sDT;
    private String ngaySinh;
    private String email;

    public KhachHang() {
    }

    public KhachHang(String maKh, String tenKh, String sDT, String ngaySinh, String email, String soThich, String diaChi) {
        this.maKh = maKh;
        this.tenKh = tenKh;
        this.sDT = sDT;
        this.ngaySinh = ngaySinh;
        this.email = email;

    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getsDT() {
        return sDT;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   

}
