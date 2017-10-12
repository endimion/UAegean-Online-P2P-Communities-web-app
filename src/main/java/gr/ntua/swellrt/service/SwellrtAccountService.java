/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.service;

import gr.ntua.swellrt.model.dmo.AccountBuilder.SwellrtAccountMngDMO;

/**
 *
 * @author nikos
 */
public interface SwellrtAccountService {

    public void saveOrUpdate(SwellrtAccountMngDMO account);

    public void saveOrUpdateTeemAttributes(SwellrtAccountMngDMO account);
    
    public SwellrtAccountMngDMO findByToken(String token);

    public SwellrtAccountMngDMO findByEid(String eid);
    public SwellrtAccountMngDMO findById(String id);
}
