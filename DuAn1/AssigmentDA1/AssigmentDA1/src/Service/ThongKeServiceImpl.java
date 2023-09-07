/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.HoaDon;
import Repositories.ThongKeRepository;
import java.util.List;

/**
 *
 * @author fuoc
 */
public class ThongKeServiceImpl implements ThongKeService {

    private ThongKeRepository thongKeRepository = new ThongKeRepository();

    @Override
    public List<HoaDon> getall(int thang, int nam) {
        return thongKeRepository.select1(thang, nam);
    }

    @Override
    public List<HoaDon> getngay(String c1, String c2) {
        return thongKeRepository.findngay(c1, c2);
    }

    @Override
    public List<HoaDon> getdthn(int ngay, int thang, int nam) {
        return thongKeRepository.selectslhn(ngay, thang, nam);
    }

    @Override
    public List<HoaDon> gethoadonhomnay(int ngay, int thang, int nam) {
        return thongKeRepository.selecthoadonhomnay(ngay, thang, nam);
    }

}
