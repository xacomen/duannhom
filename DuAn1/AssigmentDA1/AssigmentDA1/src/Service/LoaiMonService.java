/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.LoaiMon;
import java.util.List;

/**
 *
 * @author fuoc
 */
public interface LoaiMonService {

    List<LoaiMon> getAll();

//    List<LoaiMon> getIdmenu(String maloaimon);
    LoaiMon getten(String manv);

    LoaiMon getone(String id);

    void insert(LoaiMon loaiMon);

    void delete(String manv);

    void update(LoaiMon loaiMon);
}
