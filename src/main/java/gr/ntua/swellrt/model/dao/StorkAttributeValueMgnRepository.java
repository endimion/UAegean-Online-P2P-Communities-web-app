/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.model.dao;

import gr.ntua.swellrt.model.dmo.StorkAttributeValueMongoDMO;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author nikos
 */
public interface StorkAttributeValueMgnRepository extends MongoRepository<StorkAttributeValueMongoDMO, Long> {

    public List<StorkAttributeValueMongoDMO> findAll();

    public StorkAttributeValueMongoDMO save(StorkAttributeValueMongoDMO eid);

 
}
