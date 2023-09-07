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
public class KhuVuc {

    private String MaKhuVuc;
    private String TenKhuVuc;

    @Override
    public String toString() {
        return this.MaKhuVuc;
    }

    public String getMaKhuVuc() {
        return MaKhuVuc;
    }

    public void setMaKhuVuc(String MaKhuVuc) {
        this.MaKhuVuc = MaKhuVuc;
    }

    public String getTenKhuVuc() {
        return TenKhuVuc;
    }

    public void setTenKhuVuc(String TenKhuVuc) {
        this.TenKhuVuc = TenKhuVuc;
    }

    public KhuVuc() {
    }

    public KhuVuc(String MaKhuVuc, String TenKhuVuc) {
        this.MaKhuVuc = MaKhuVuc;
        this.TenKhuVuc = TenKhuVuc;
    }
}
