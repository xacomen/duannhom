/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Service;

import Models.KhachHang;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface KhachHangService {

    List<KhachHang> getAll();

    KhachHang getone(String sdt);

    void insert(KhachHang kh);

    void delete(String maKh);

    void update(KhachHang kh);

}
