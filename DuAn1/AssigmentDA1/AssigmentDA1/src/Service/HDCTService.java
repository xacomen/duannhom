/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.HDCT;
import java.util.List;

/**
 *
 * @author fuoc
 */
public interface HDCTService {

    List<HDCT> getAll();

    List<HDCT> getAll1(String id);

    List<HDCT> check(String mahd, String Mamon);

    List<HDCT> getById(String mahd);

    void insert(HDCT Hdct);

    void delete(String mahdct);

    void update(HDCT hdct);
}
