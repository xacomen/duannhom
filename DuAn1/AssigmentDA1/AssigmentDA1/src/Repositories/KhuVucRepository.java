/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import Models.KhuVuc;
import Ulties.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fuoc
 */
public class KhuVucRepository {

    public void insert(KhuVuc model) {
        String sql = "INSERT INTO KHUVUC (MaKV, TenKV) VALUES (?, ?)";
        DBContext.executeUpdate(sql, model.getMaKhuVuc(), model.getTenKhuVuc());
    }

    public void update(KhuVuc model) {
        String sql = "UPDATE KHUVUC SET TenKV=? WHERE MaKV=?";
        DBContext.executeUpdate(sql, model.getTenKhuVuc(), model.getMaKhuVuc());
    }

    public void delete(String MaloaiMon) {
        String sql = "DELETE FROM KHUVUC WHERE MaKV=?";
        DBContext.executeUpdate(sql, MaloaiMon);
    }

    public List<KhuVuc> select() {
        String sql = "SELECT * FROM KHUVUC";
        return select(sql);
    }

    public KhuVuc findById(String manv) {
        String sql = "SELECT * FROM KHUVUC WHERE MaKV=?";
        List<KhuVuc> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<KhuVuc> select(String sql, Object... args) {
        List<KhuVuc> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = DBContext.executeQuery(sql, args);
                while (rs.next()) {
                    KhuVuc model = readFromResultSet(rs);
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

    private KhuVuc readFromResultSet(ResultSet rs) throws SQLException {
        KhuVuc model = new KhuVuc();
        model.setMaKhuVuc(rs.getString("MaKV"));
        model.setTenKhuVuc(rs.getString("TenKV"));

        return model;
    }
}
