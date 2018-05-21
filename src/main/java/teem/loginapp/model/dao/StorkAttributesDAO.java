/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.model.dao;

import teem.loginapp.model.dmo.StorkAttributesDMO;
import java.util.List;


/**
 *
 * @author nikos
 */
public interface StorkAttributesDAO {
    
    public List<StorkAttributesDMO> findAll();
    
    public StorkAttributesDMO findByName(String attributeName);
    
     public List<StorkAttributesDMO> getEnabledAttributes();

}
