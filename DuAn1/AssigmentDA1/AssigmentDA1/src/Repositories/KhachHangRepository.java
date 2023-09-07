/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;

import Models.KhachHang;
import Ulties.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class KhachHangRepository {

    public void insert(KhachHang model) {
        String sql = "INSERT INTO KHACHHANG (MaKH, TenKh, SDT, ngaySinh, email) VALUES (?, ?, ?, ?, ?)";
        DBContext.executeUpdate(sql, model.getMaKh(), model.getTenKh(), model.getsDT(), model.getNgaySinh(), model.getEmail());
    }

    public void update(KhachHang model) {
        String sql = "UPDATE KHACHHANG SET TenKh=?, SDT=?, ngaySinh=?, email=?  WHERE MaKH=?";
        DBContext.executeUpdate(sql, model.getTenKh(), model.getsDT(), model.getNgaySinh(), model.getEmail(), model.getMaKh());
    }

    public void delete(String sDT) {
        String sql = "DELETE FROM KHACHHANG WHERE MaKH=?";
        DBContext.executeUpdate(sql, sDT);
    }

    public List<KhachHang> select() {
        String sql = "SELECT * FROM KHACHHANG";
        return select(sql);
    }
    
     public KhachHang findById(String sdt) {
        String sql = "SELECT * FROM KHACHHANG WHERE MaKH=?";
        List<KhachHang> list = select(sql, sdt);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<KhachHang> select(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = DBContext.executeQuery(sql, args);
                while (rs.next()) {
                    KhachHang model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private KhachHang readFromResultSet(ResultSet rs) throws SQLException {
        KhachHang model = new KhachHang();
        model.setMaKh(rs.getString("MaKH"));
        model.setTenKh(rs.getString("TenKh"));
        model.setsDT(rs.getString("SDT"));
        model.setNgaySinh(rs.getString("ngaySinh"));
        model.setEmail(rs.getString("email"));
        return model;
    }
}
