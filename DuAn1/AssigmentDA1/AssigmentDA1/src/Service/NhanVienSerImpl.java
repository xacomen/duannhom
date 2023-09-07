/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.NhanVien;
import Repositories.NhanVienRepository;
import java.util.List;

/**
 *
 * @author fuoc
 */
public class NhanVienSerImpl implements NhanVienSer {

    public NhanVienRepository nhanVienRepo = new NhanVienRepository();

    @Override
    public List<NhanVien> getAll() {
        return nhanVienRepo.select();
    }

    @Override
    public NhanVien getone(String id) {
        return nhanVienRepo.findById(id);
    }

    @Override
    public void insert(NhanVien nhanVien) {
        nhanVienRepo.insert(nhanVien);
    }

    @Override
    public void delete(String manv) {
        nhanVienRepo.delete(manv);
    }

    @Override
    public void update(NhanVien nhanVien) {
        nhanVienRepo.update(nhanVien);
    }

}
