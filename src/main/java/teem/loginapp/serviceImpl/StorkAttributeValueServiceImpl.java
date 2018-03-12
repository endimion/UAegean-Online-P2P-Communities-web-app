/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.serviceImpl;

import teem.loginapp.model.dao.StorkAttributeMngRepository;
import teem.loginapp.model.dao.StorkAttributeValueMgnRepository;
import teem.loginapp.model.dmo.StorkAttributeValueMongoDMO;
import teem.loginapp.service.StorkAttributeValueService;
import teem.loginapp.utils.MongoUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nikos
 */
@Service
public class StorkAttributeValueServiceImpl implements StorkAttributeValueService {

    @Autowired
    private StorkAttributeValueMgnRepository attrValRepo;

    @Autowired
    private StorkAttributeMngRepository attrRepo;

    @Override
    @Transactional
    public List<StorkAttributeValueMongoDMO> findAll() {
        return attrValRepo.findAll();
    }

    @Override
    @Transactional
    public void save(StorkAttributeValueMongoDMO attributeValue) {
        MongoUtils.cascadeSaveToAttributes(attributeValue, attrValRepo, attrRepo);
    }

}
