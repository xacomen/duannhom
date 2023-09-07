/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import Models.Ban;
import Ulties.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fuoc
 */
public class BanRepository {

    public void insert(Ban model) {
        String sql = "INSERT INTO BAN (MaBan,TenBan,MaKV_FK) VALUES (?, ?,?)";
        DBContext.executeUpdate(sql, model.getMaBan(), model.getTenBan(), model.getMaKV());
    }

    public void update(Ban model) {
        String sql = "UPDATE BAN SET TenBan=?,MaKV_FK=? WHERE MaBan=?";
        DBContext.executeUpdate(sql, model.getTenBan(), model.getMaKV(), model.getMaBan());
    }

    public void delete(String MaloaiMon) {
        String sql = "DELETE FROM BAN WHERE MaBan=?";
        DBContext.executeUpdate(sql, MaloaiMon);
    }

    public List<Ban> select() {
        String sql = "SELECT * FROM BAN order by MaKV_FK ASC";
        return select(sql);
    }

    public Ban findById(String manv) {
        String sql = "SELECT * FROM BAN WHERE MaBan=?";
        List<Ban> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<Ban> findByIdKhuVuc(String makv) {
        String sql = "SELECT * FROM BAN where MaKV_FK='" + makv + "'";
        return select(sql);
    }

    private List<Ban> select(String sql, Object... args) {
        List<Ban> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = DBContext.executeQuery(sql, args);
                while (rs.next()) {
                    Ban model = readFromResultSet(rs);
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

    private Ban readFromResultSet(ResultSet rs) throws SQLException {
        Ban model = new Ban();
        model.setMaBan(rs.getString("MaBan"));
        model.setTenBan(rs.getString("TenBan"));
        model.setMaKV(rs.getString("MaKV_FK"));

        return model;
    }
}
