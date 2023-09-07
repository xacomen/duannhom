/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.HoaDon;
import Repositories.HoaDonRepository;
import java.util.List;

/**
 *
 * @author fuoc
 */
public class HoaDonServiceImpl implements HoaDonService {

    private HoaDonRepository hoaDonRepository = new HoaDonRepository();

    @Override
    public List<HoaDon> getAll() {
        return hoaDonRepository.select();
    }

    @Override
    public List<HoaDon> getAll1() {
        return hoaDonRepository.select1();
    }

    @Override
    public HoaDon getone(String id) {
        return hoaDonRepository.findById(id);
    }

    @Override
    public void insert(HoaDon hoaDon) {
        hoaDonRepository.insert(hoaDon);
    }

    @Override
    public void delete(String mahd) {
        hoaDonRepository.delete(mahd);
    }

    @Override
    public void update(HoaDon hoaDon) {
        hoaDonRepository.update(hoaDon);
    }

    @Override
    public List<HoaDon> getmaban(String maban) {
        return hoaDonRepository.selectmaban(maban);
    }

    @Override
    public void updateoffhd(String maban, String mahd) {
        hoaDonRepository.updateoffhd(maban, mahd);
    }

    @Override
    public List<HoaDon> getHD() {
        return hoaDonRepository.select2();
    }

    @Override
    public List<HoaDon> search(String mahd) {
        return hoaDonRepository.search(mahd);
    }

}
