/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Models.KhachHang;
import Repositories.KhachHangRepository;
import java.util.List;

/**
 *
 * @author DELL
 */
public class KhachHangServiceImpl implements KhachHangService {

    private KhachHangRepository khachHangRepository = new KhachHangRepository();

    @Override
    public List<KhachHang> getAll() {
        return khachHangRepository.select();
    }

    @Override
    public void insert(KhachHang kh) {
       khachHangRepository.insert(kh);
    }

    @Override
    public void delete(String maKh) {
        khachHangRepository.delete(maKh);
    }

    @Override
    public void update(KhachHang kh) {
        khachHangRepository.update(kh);
    }

    @Override
    public KhachHang getone(String sdt) {
        return khachHangRepository.findById(sdt);
    }

}
