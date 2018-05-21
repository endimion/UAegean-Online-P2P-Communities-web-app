/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.service;

import teem.loginapp.model.dmo.PersonSqlDMO;

/**
 *
 * @author nikos
 */
@Deprecated
public interface EidService {
    
    @Deprecated
    public PersonSqlDMO findByEid(String eid);
    @Deprecated
    public PersonSqlDMO findByToken(String token);
    @Deprecated
    public void saveOrUpdateEidAttribute(PersonSqlDMO eid);
    
    
}
