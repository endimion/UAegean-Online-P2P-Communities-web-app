/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.serviceImpl;

import teem.loginapp.model.dao.SwellrtAccountRepository;
import teem.loginapp.model.dmo.AccountBuilder.SwellrtAccountMngDMO;

import teem.loginapp.service.SwellrtAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author nikos
 */
@Service
public class SwellrtAccountServiceImpl implements SwellrtAccountService {
    
    private final static Logger LOG = LoggerFactory.getLogger(SwellrtAccountService.class);

    @Autowired
    private SwellrtAccountRepository accountRepo;

    
    /** Saves or Updates the given SwellrtAccount object. 
        However, in the case of update, it does not change (if found)
        the existing values of CurrentAddress, Gender,email,UAgeanID
    *   @param account, the SwellrtAccountMngDMO object to be updated
    */
    @Override
    public void saveOrUpdate(SwellrtAccountMngDMO account) {

        SwellrtAccountMngDMO oldAccount = accountRepo.findByEid(account.getEid());
        /**
         * Values that should be overwritten by existing values
         * CurrentAddress
         * Gender 
         * email
         */
        if (oldAccount != null) {
            LOG.info(" FOUND OLD ACCOUNT " + oldAccount.getId());
            //overwrite the value of the received FamilyName value, if it exists
            account = overWriteWithOldValue(oldAccount,account,"CurrentAddress");
            account = overWriteWithOldValue(oldAccount,account,"Gender");
            
//            if (oldAccount.getAttributes().get("CurrentAddress") != null
//                    && !StringUtils.isEmpty(oldAccount.getAttributes().get("CurrentAddress").getValue())) {
//                account.getAttributes().put("CurrentAddress", oldAccount.getAttributes().get("CurrentAddress"));
//            }
//            if (oldAccount.getAttributes().get("Gender") != null
//                    && !StringUtils.isEmpty(oldAccount.getAttributes().get("Gender").getValue())) {
//                account.getAttributes().put("Gender", oldAccount.getAttributes().get("Gender"));
//            }
//            
            
            LOG.info(" FOUND EMAIL " + oldAccount.getHuman().getEmail());
            //overwrite the value of the e-mail value if null
            if(!StringUtils.isEmpty(oldAccount.getHuman().getEmail())){
                account.getHuman().setEmail(oldAccount.getHuman().getEmail());
            }
        }
        accountRepo.save(account);
    }

    @Override
    public SwellrtAccountMngDMO findByToken(String token) {
        return accountRepo.findByToken(token);
    }

    @Override
    public SwellrtAccountMngDMO findByEid(String eid) {
        return accountRepo.findByEid(eid);
    }

    
    
    /** Saves or Updates the given SwellrtAccount object. 
        However, in the case of update, it does not change (if found)
        the existing values of FamilyName, FirstName,DateOfBirth, BirthName
    *   @param account, the SwellrtAccountMngDMO object to be updated
    */
    @Override
    public void saveOrUpdateTeemAttributes(SwellrtAccountMngDMO account) {
        
        SwellrtAccountMngDMO oldAccount = accountRepo.findByEid(account.getEid());
        /**
         *  Values that should be overwritten by existing values
         * FamilyName
         * FirstName 
         * DateOfBirth
         * BirthName
         */
        if (oldAccount != null) {
            LOG.info(" FOUND OLD ACCOUNT " + oldAccount.getId());
            //overwrite the value of the received FamilyName value, if it exists
            account = overWriteWithOldValue(oldAccount,account,"FamilyName");
            account = overWriteWithOldValue(oldAccount,account,"DateOfBirth");
            account = overWriteWithOldValue(oldAccount,account,"BirthName");
//            if (oldAccount.getAttributes().get("FamilyName") != null
//                    && !StringUtils.isEmpty(oldAccount.getAttributes().get("FamilyName").getValue())) {
//                account.getAttributes().put("FamilyName", oldAccount.getAttributes().get("FamilyName"));
//            }
//            if (oldAccount.getAttributes().get("FirstName") != null
//                    && !StringUtils.isEmpty(oldAccount.getAttributes().get("FirstName").getValue())) {
//                account.getAttributes().put("FirstName", oldAccount.getAttributes().get("FirstName"));
//            }
//            if (oldAccount.getAttributes().get("DateOfBirth") != null
//                    && !StringUtils.isEmpty(oldAccount.getAttributes().get("DateOfBirth").getValue())) {
//                account.getAttributes().put("DateOfBirth", oldAccount.getAttributes().get("DateOfBirth"));
//            }
//            if (oldAccount.getAttributes().get("BirthName") != null
//                    && !StringUtils.isEmpty(oldAccount.getAttributes().get("BirthName").getValue())) {
//                account.getAttributes().put("BirthName", oldAccount.getAttributes().get("BirthName"));
//            }
            
            LOG.info(" FOUND EMAIL " + oldAccount.getHuman().getEmail());
            //overwrite the value of the e-mail value if null
            if(!StringUtils.isEmpty(oldAccount.getHuman().getEmail()) 
                    && StringUtils.isEmpty(account.getHuman().getEmail())){
                account.getHuman().setEmail(oldAccount.getHuman().getEmail());
            }
        }
        accountRepo.save(account);
    }
    
    
    /**
     * 
     * @param oldAccount, a SwellrtAccount object retrieved from the database
     * @param newAccount, a received SwellrtAccount object
     * @param attributeName, the name of the attribute you want to 
     * @return 
     */
    private SwellrtAccountMngDMO overWriteWithOldValue(SwellrtAccountMngDMO oldAccount, SwellrtAccountMngDMO newAccount, 
            String attributeName){
    
            if (oldAccount.getAttributes().get(attributeName) != null
                    && !StringUtils.isEmpty(oldAccount.getAttributes().get(attributeName).getValue())) {
                newAccount.getAttributes().put(attributeName, oldAccount.getAttributes().get(attributeName));
            }
        
        return newAccount;
    }

    @Override
    public SwellrtAccountMngDMO findById(String id) {
        return accountRepo.findById(id);
    }
    

}
