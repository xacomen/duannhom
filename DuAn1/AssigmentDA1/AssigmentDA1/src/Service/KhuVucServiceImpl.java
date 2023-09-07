/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Models.KhuVuc;
import Repositories.KhuVucRepository;
import java.util.List;

/**
 *
 * @author fuoc
 */
public class KhuVucServiceImpl implements KhuVucService {

    private KhuVucRepository khuVucRepository = new KhuVucRepository();

    @Override
    public List<KhuVuc> getAll() {
        return khuVucRepository.select();
    }

    @Override
    public KhuVuc getone(String id) {
        return khuVucRepository.findById(id);
    }

    @Override
    public void insert(KhuVuc khuVuc) {
      khuVucRepository.insert(khuVuc);
    }

    @Override
    public void delete(String manv) {
       khuVucRepository.delete(manv);
    }

    @Override
    public void update(KhuVuc khuVuc) {
        khuVucRepository.update(khuVuc);
    }

}
