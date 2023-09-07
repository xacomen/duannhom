/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.Ban;
import Repositories.BanRepository;
import java.util.List;

/**
 *
 * @author fuoc
 */
public class BanServiceImpl implements BanService {

    private BanRepository banRepository = new BanRepository();

    @Override
    public List<Ban> getAll() {
        return banRepository.select();
    }

    @Override
    public Ban getone(String id) {
        return banRepository.findById(id);
    }

    @Override
    public void insert(Ban nhanVien) {
        banRepository.insert(nhanVien);
    }

    @Override
    public void delete(String manv) {
        banRepository.delete(manv);
    }

    @Override
    public void update(Ban ban) {
        banRepository.update(ban);
    }

    @Override
    public List<Ban> getIdKhuVuc(String makhuvuc) {
        return banRepository.findByIdKhuVuc(makhuvuc);
    }

}
