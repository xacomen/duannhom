/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.KhuVuc;
import java.util.List;

/**
 *
 * @author fuoc
 */
public interface KhuVucService {

    List<KhuVuc> getAll();

    KhuVuc getone(String id);

    void insert(KhuVuc khuVuc);

    void delete(String manv);

    void update(KhuVuc khuVuc);
}
