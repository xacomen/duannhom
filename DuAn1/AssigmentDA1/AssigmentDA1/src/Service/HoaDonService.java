/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.HoaDon;
import java.util.List;

/**
 *
 * @author fuoc
 */
public interface HoaDonService {

    List<HoaDon> getAll();

    List<HoaDon> getAll1();

    HoaDon getone(String id);

    List<HoaDon> getHD();

    List<HoaDon> search(String mahd);

    List<HoaDon> getmaban(String maban);

    void updateoffhd(String maban, String mahd);

    void insert(HoaDon hoaDon);

    void delete(String mahd);

    void update(HoaDon hoaDon);
}
