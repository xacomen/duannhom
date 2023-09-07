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
public class NhanVien {

    private String MaNV;
    private String TenNV;
    private String Password;
    private String Hinh;
    private String CMND;
    private String DiaChi;
    private String DienThoai;
    private Date NgaySinh;
    private Date NgayVaoLam;
    private boolean VaiTro;
    private boolean ViTri;

    @Override
    public String toString() {
        return this.TenNV;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String DienThoai) {
        this.DienThoai = DienThoai;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public Date getNgayVaoLam() {
        return NgayVaoLam;
    }

    public void setNgayVaoLam(Date NgayVaoLam) {
        this.NgayVaoLam = NgayVaoLam;
    }

    public boolean getVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(boolean VaiTro) {
        this.VaiTro = VaiTro;
    }

    public boolean getViTri() {
        return ViTri;
    }

    public void setViTri(boolean ViTri) {
        this.ViTri = ViTri;
    }

    public NhanVien() {
    }

    public NhanVien(String MaNV, String TenNV, String Password, String Hinh, String CMND, String DiaChi, String DienThoai, Date NgaySinh, Date NgayVaoLam, boolean VaiTro, boolean ViTri) {
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.Password = Password;
        this.Hinh = Hinh;
        this.CMND = CMND;
        this.DiaChi = DiaChi;
        this.DienThoai = DienThoai;
        this.NgaySinh = NgaySinh;
        this.NgayVaoLam = NgayVaoLam;
        this.VaiTro = VaiTro;
        this.ViTri = ViTri;
    }

}
