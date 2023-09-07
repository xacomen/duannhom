/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.Ban;
import java.util.List;

/**
 *
 * @author fuoc
 */
public interface BanService {

    List<Ban> getAll();

    Ban getone(String id);

    List<Ban> getIdKhuVuc(String makhuvuc);

    void insert(Ban nhanVien);

    void delete(String manv);

    void update(Ban ban);
}
