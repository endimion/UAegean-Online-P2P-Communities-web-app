/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.service;

import teem.loginapp.model.dmo.StorkAttributeValueMongoDMO;
import java.util.List;

/**
 *
 * @author nikos
 */
public interface StorkAttributeValueService {
    public List<StorkAttributeValueMongoDMO> findAll();
    public void save(StorkAttributeValueMongoDMO attributeValue);
}
