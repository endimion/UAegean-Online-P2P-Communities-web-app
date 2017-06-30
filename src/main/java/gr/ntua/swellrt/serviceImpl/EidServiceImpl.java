/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.serviceImpl;

 
import gr.ntua.swellrt.model.dao.PersonDAO;
import gr.ntua.swellrt.model.dao.StorkAttributesDAO;
import gr.ntua.swellrt.model.dmo.PersonSqlDMO;
import gr.ntua.swellrt.model.dmo.StorkAttributeValuesDMO;
import gr.ntua.swellrt.model.dmo.StorkAttributesDMO;
import gr.ntua.swellrt.service.EidService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author nikos
 */
@Service
@Transactional
public class EidServiceImpl implements EidService {

    @Autowired
    private PersonDAO eidDAO;
    
    @Autowired
    private StorkAttributesDAO attributesDAO;
    
    @Override
    public PersonSqlDMO findByEid(String eid) {
        return eidDAO.findByEid(eid);
    }

    @Override
    public PersonSqlDMO findByToken(String token) {
        return eidDAO.findByToken(token);
    }

    @Override
    public void saveOrUpdateEidAttribute(PersonSqlDMO eid) {
        PersonSqlDMO oldEid = eidDAO.findByEid(eid.getEid());

        Map<String, String> receivedAttrNameValMap = new HashMap();
        Map<String, StorkAttributesDMO> receivedAttrNameAttrDmoMap = new HashMap();

        eid.getAttributesValues().forEach(attrval -> {
            receivedAttrNameValMap.put(attrval.getAttribute().getName(), attrval.getValue());
            receivedAttrNameAttrDmoMap.put(attrval.getAttribute().getName(), attrval.getAttribute());
        });

        if (oldEid != null) {
//            oldEid.setAttributesValues(eid.getAttributesValues());

            List<String> existingAttributeNames = new ArrayList();
            //update values of existing attributes
            oldEid.getAttributesValues().forEach(attval -> {
                String newVal = receivedAttrNameValMap.get(attval.getAttribute().getName());
                if (newVal != null && !newVal.equals(attval.getValue())) {
                    attval.setValue(newVal);
                }
                existingAttributeNames.add(attval.getAttribute().getName());
            });

            //add newly discovered attributes
            receivedAttrNameValMap.forEach( (attr,val)->{
                if(!existingAttributeNames.contains(attr)){
                    StorkAttributesDMO tempAttr = attributesDAO.findByName(attr);
                    StorkAttributeValuesDMO atrVal = new StorkAttributeValuesDMO();
                    atrVal.setValue(val);
                    atrVal.setAttribute(tempAttr);
                    oldEid.getAttributesValues().add(atrVal);
                }
            });
            
            oldEid.setToken(eid.getToken());
            oldEid.setTimestamp(eid.getTimestamp());
           
            if(!StringUtils.isEmpty(eid.getEmail())){
                oldEid.setEmail(eid.getEmail());
            }
            
            eidDAO.saveEid(oldEid);
        } else {
            eidDAO.saveEid(eid);
        }

    }

}
