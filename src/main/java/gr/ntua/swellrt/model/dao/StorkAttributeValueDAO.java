/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.model.dao;

import gr.ntua.swellrt.model.dmo.StorkAttributeValuesDMO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nikos
 */
public interface StorkAttributeValueDAO  {
    
    public List<StorkAttributeValuesDMO> findAll();
    
}
