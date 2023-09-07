/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.HDCT;
import Repositories.HDCTRepository;
import java.util.List;

/**
 *
 * @author fuoc
 */
public class HDCTServiceImpl implements HDCTService {

    private HDCTRepository hoaCTRepository = new HDCTRepository();

    @Override
    public List<HDCT> getAll() {
        return hoaCTRepository.select();
    }

    @Override
    public List<HDCT> getAll1(String id) {
        return hoaCTRepository.select1(id);
    }

//    @Override
//    public HDCT getone(String id) {
//        
//    }
    @Override
    public void insert(HDCT Hdct) {
        hoaCTRepository.insert(Hdct);
    }

    @Override
    public void delete(String mahdct) {
        hoaCTRepository.delete(mahdct);
    }

    @Override
    public void update(HDCT hdct) {
        hoaCTRepository.update(hdct);
    }

    @Override
    public List<HDCT> check(String mahd, String Mamon) {
        return hoaCTRepository.check(mahd, Mamon);
    }

    @Override
    public List<HDCT> getById(String mahd) {
        return hoaCTRepository.findById2(mahd);
    }

}
