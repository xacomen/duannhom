/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import Models.Mon;
import Ulties.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fuoc
 */
public class MonRepository {

    public void insert(Mon model) {
        String sql = "INSERT INTO MON (MaMon, TenMon, Gia,HinhAnh, MaLoai_FK) VALUES (?,?,?,?,?)";
        DBContext.executeUpdate(sql, model.getMaMon(), model.getTenMon(), model.getGia(), model.getHinh(), model.getMaLoaiMon());
    }

    public void update(Mon model) {
        String sql = "UPDATE MON SET TenMon=?,Gia=?,HinhAnh=?,MaLoai_FK=? WHERE MaMon=?";
        DBContext.executeUpdate(sql, model.getTenMon(), model.getGia(), model.getHinh(), model.getMaLoaiMon());
    }

    public void delete(String MaMon) {
        String sql = "DELETE FROM MON WHERE MaMon=?";
        DBContext.executeUpdate(sql, MaMon);
    }

    public List<Mon> select() {
        String sql = "SELECT * FROM MON";
        return select(sql);
    }

    public List<Mon> findtenmon(String ten) {
        String sql = "SELECT * FROM THUCDON where TenMon='" + ten + "'";
        return select(sql);
    }

    public List<Mon> selectByKeyword(String tenmon) {
        String sql = "SELECT * FROM MON WHERE TenMon LIKE ?";
        return select(sql, "%" + tenmon + "%");
    }

    public List<Mon> findByIdThucDon(String maloaimon) {
        String sql = "SELECT * FROM MON WHERE MaLoai_FK='" + maloaimon + "'";
        return select(sql);
    }

    public List<Mon> findByIdMon(String maimon) {
        String sql = "SELECT * FROM MON where MaMon='" + maimon + "'";
        return select(sql);
    }

    public Mon findById(String manv) {
        String sql = "SELECT * FROM MON WHERE MaMon=?";
        List<Mon> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<Mon> findByIdLoaiMon(String maloaimon) {
        String sql = "SELECT * FROM MON where MaLoai_FK='" + maloaimon + "'";
        return select(sql);
    }

    private List<Mon> select(String sql, Object... args) {
        List<Mon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = DBContext.executeQuery(sql, args);
                while (rs.next()) {
                    Mon model = readFromResultSet(rs);
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

    private Mon readFromResultSet(ResultSet rs) throws SQLException {
        Mon model = new Mon();
        model.setMaMon(rs.getString("MaMon"));
        model.setTenMon(rs.getString("TenMon"));
        model.setGia(rs.getFloat("Gia"));
        model.setHinh(rs.getString("HinhAnh"));
        model.setMaLoaiMon(rs.getString("MaLoai_FK"));
        return model;
    }
}
