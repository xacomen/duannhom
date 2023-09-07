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
public class Ban {

    private String MaBan;
    private String TenBan;
    private String MaKV;

    @Override
    public String toString() {
        return this.MaBan;
    }

    public String getMaBan() {
        return MaBan;
    }

    public void setMaBan(String MaBan) {
        this.MaBan = MaBan;
    }

    public String getTenBan() {
        return TenBan;
    }

    public void setTenBan(String TenBan) {
        this.TenBan = TenBan;
    }

    public String getMaKV() {
        return MaKV;
    }

    public void setMaKV(String MaKV) {
        this.MaKV = MaKV;
    }

    public Ban() {
    }

    public Ban(String MaBan, String TenBan, String MaKV) {
        this.MaBan = MaBan;
        this.TenBan = TenBan;
        this.MaKV = MaKV;
    }

}
