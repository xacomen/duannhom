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
public interface ThongKeService {

    List<HoaDon> getall(int thang, int nam);

    List<HoaDon> getngay(String c1, String c2);

    List<HoaDon> getdthn(int ngay, int thang, int nam);

    List<HoaDon> gethoadonhomnay(int ngay, int thang, int nam);
}
