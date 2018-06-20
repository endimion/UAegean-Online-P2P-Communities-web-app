/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.service;

import java.util.List;
import teem.loginapp.model.dmo.AccountBuilder.SwellrtAccountMngDMO;

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

    public List<SwellrtAccountMngDMO> findByFirstAndLast(String firstName, String lastName);
}
