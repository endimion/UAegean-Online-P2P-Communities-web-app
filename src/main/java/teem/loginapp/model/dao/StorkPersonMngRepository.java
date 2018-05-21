/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.model.dao;

import teem.loginapp.model.dmo.StorkPersonMngDMO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author nikos
 */
public interface StorkPersonMngRepository extends MongoRepository<StorkPersonMngDMO,Long>{
    
    public StorkPersonMngDMO findByEid(String eid);
    public StorkPersonMngDMO findByToken(String token);
    
    
    
}
