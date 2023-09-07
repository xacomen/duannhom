/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import Models.HoaDon;
import Ulties.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fuoc
 */
public class HoaDonRepository {

    public void insert(HoaDon model) {
        String sql = "INSERT INTO HOADON (MaHD, MaBan_FK, MaNV_FK,NgayHD,TongTien,Trangthai) VALUES (?, ?, ?,?,?,?)";
        DBContext.executeUpdate(sql, model.getMahd(), model.getMaban(), model.getManv(), model.getNgaylap(), model.getTongtien(), model.getTrangthai());
    }

    public void update(HoaDon model) {
        String sql = "UPDATE HOADON SET MaBan_FK=?, MaNV_FK=?,NgayHD=?,TongTien=?,Trangthai=? where MaHD=?";
        DBContext.executeUpdate(sql, model.getMaban(), model.getManv(), model.getNgaylap(), model.getTongtien(), model.getTrangthai(), model.getMahd());

    }

    public void updateoffhd(String maban, String mahd) {
        String sql = "UPDATE HOADON SET MaBan_FK='" + maban + "', Trangthai=0 where MaHD='" + mahd + "'";
        DBContext.executeUpdate(sql);

    }

    public void delete(String Username) {
        String sql = "DELETE FROM HOADON WHERE MaHD=?";
        DBContext.executeUpdate(sql, Username);
    }

    public List<HoaDon> select() {
        String sql = "SELECT * FROM HOADON where Trangthai=1";
        return select(sql);
    }

    public List<HoaDon> selectmaban(String maban) {
        String sql = "SELECT * FROM HOADON where Trangthai=1 and MaBan_FK='" + maban + "'";
        return select(sql);
    }

    public List<HoaDon> select1() {
        String sql = "SELECT * FROM HOADON";
        return select(sql);
    }

    public List<HoaDon> select2() {
        String sql = "SELECT * FROM HOADON WHERE TongTien != 0";
        return select(sql);
    }
    public List<HoaDon> search(String mahd) {
        String sql = "SELECT * FROM HOADON WHERE MaHD LIKE ? and TongTien != 0";
        return select(sql, "%" + mahd + "%");
    }

    public HoaDon findById(String manv) {
        String sql = "SELECT * FROM HOADON WHERE MaHD=?";
        List<HoaDon> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<HoaDon> select(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = DBContext.executeQuery(sql, args);
                while (rs.next()) {
                    HoaDon model = readFromResultSet(rs);
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

    private HoaDon readFromResultSet(ResultSet rs) throws SQLException {
        HoaDon model = new HoaDon();
        model.setMahd(rs.getString("MaHD"));
        model.setMaban(rs.getString("MaBan_FK"));
        model.setManv(rs.getString("MaNV_FK"));
        model.setTongtien(rs.getFloat("TongTien"));
        model.setNgaylap(rs.getDate("NgayHD"));
        model.setTrangthai(rs.getBoolean("Trangthai"));

        return model;
    }
}
