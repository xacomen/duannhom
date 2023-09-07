/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.NhanVien;
import java.util.List;

/**
 *
 * @author fuoc
 */
public interface NhanVienSer {

    List<NhanVien> getAll();

    NhanVien getone(String id);

    void insert(NhanVien nhanVien);

    void delete(String manv);

    void update( NhanVien nhanVien);

}
