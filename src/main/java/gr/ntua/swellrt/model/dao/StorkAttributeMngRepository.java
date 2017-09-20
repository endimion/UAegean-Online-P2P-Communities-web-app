/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.model.dao;

import gr.ntua.swellrt.model.dmo.StrokAttributesMongoDMO;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author nikos
 */
public interface StorkAttributeMngRepository extends MongoRepository<StrokAttributesMongoDMO, Long>{
    
    public StrokAttributesMongoDMO findFirstByName(String name);
    public StrokAttributesMongoDMO findFirstByEidasName(String eidasName);
    public List<StrokAttributesMongoDMO> findAll();
    public StrokAttributesMongoDMO save(StrokAttributesMongoDMO attribute);
     
}
