/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.model.dao;

import java.util.List;
import teem.loginapp.model.dmo.AccountBuilder.SwellrtAccountMngDMO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author nikos
 */
public interface SwellrtAccountRepository extends MongoRepository<SwellrtAccountMngDMO, Long> {

    public SwellrtAccountMngDMO findByToken(String token);

    public SwellrtAccountMngDMO findByEid(String eid);

    public SwellrtAccountMngDMO findById(String id);

    @Query("{ 'engCurrentGivenName' : ?0 , 'engCurrentFamilyName':?1}")
    public List<SwellrtAccountMngDMO> findByEngCurrentNameAndSurname(String name, String surname);
}
