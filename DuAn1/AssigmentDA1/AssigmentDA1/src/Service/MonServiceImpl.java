/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.Mon;
import Repositories.MonRepository;
import java.util.List;

/**
 *
 * @author fuoc
 */
public class MonServiceImpl implements MonService {

    private MonRepository monRepository = new MonRepository();

    @Override
    public List<Mon> getAll() {
        return monRepository.select();
    }

    @Override
    public Mon getone(String id) {
        return monRepository.findById(id);
    }

    @Override
    public void insert(Mon mon) {
        monRepository.insert(mon);
    }

    @Override
    public void delete(String manv) {
        monRepository.delete(manv);
    }

    @Override
    public void update(Mon mon) {
        monRepository.update(mon);
    }

    @Override
    public List<Mon> getIdmenu(String maloaimon) {
        return monRepository.findByIdThucDon(maloaimon);
    }

    @Override
    public List<Mon> getIdMon(String mamon) {
        return monRepository.findByIdMon(mamon);
    }

    @Override
    public List<Mon> getten(String tenmon) {
        return monRepository.selectByKeyword(tenmon);
    }

    @Override
    public List<Mon> getIdLoaiMon(String maloaimon) {
        return monRepository.findByIdLoaiMon(maloaimon);
    }

}
