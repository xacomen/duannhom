/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.Mon;
import java.util.List;

/**
 *
 * @author fuoc
 */
public interface MonService {

    List<Mon> getAll();

    List<Mon> getIdmenu(String maloaimon);

    Mon getone(String id);

    List<Mon> getIdMon(String mamon);

    List<Mon> getten(String tenmon);

    List<Mon> getIdLoaiMon(String maloaimon);

    void insert(Mon mon);

    void delete(String manv);

    void update(Mon mon);
}
