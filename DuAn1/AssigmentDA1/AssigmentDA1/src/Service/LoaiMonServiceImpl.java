/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.LoaiMon;
import Repositories.LoaiMonRepository;
import java.util.List;

/**
 *
 * @author fuoc
 */
public class LoaiMonServiceImpl implements LoaiMonService {

    private LoaiMonRepository loaiMonRepository = new LoaiMonRepository();

    @Override
    public List<LoaiMon> getAll() {
        return loaiMonRepository.select();
    }

    @Override
    public LoaiMon getone(String id) {
        return loaiMonRepository.findById(id);
    }

    @Override
    public void insert(LoaiMon loaiMon) {
        loaiMonRepository.insert(loaiMon);
    }

    @Override
    public void delete(String manv) {
        loaiMonRepository.delete(manv);
    }

    @Override
    public void update(LoaiMon loaiMon) {
        loaiMonRepository.update(loaiMon);
    }

    @Override
    public LoaiMon getten(String manv) {
        return loaiMonRepository.findByten(manv);
    }

}
