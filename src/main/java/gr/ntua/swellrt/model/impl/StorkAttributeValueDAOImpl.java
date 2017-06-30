/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.model.impl;

import gr.ntua.swellrt.model.dao.StorkAttributeValueDAO;
import gr.ntua.swellrt.model.dmo.StorkAttributeValuesDMO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nikos
 */
@Repository
public class StorkAttributeValueDAOImpl implements StorkAttributeValueDAO {

//    @Autowired
//    private SessionFactory sessionFactory;
    
     @PersistenceContext
    public EntityManager em;
    
    private final static Logger logger = LoggerFactory.getLogger(StorkAttributeValueDAOImpl.class);

    @Override
    @Transactional
    public List<StorkAttributeValuesDMO> findAll() {
        //Session session = this.sessionFactory.getCurrentSession();
        List<StorkAttributeValuesDMO> attributes = em.createQuery(" from StorkAttributeValuesDMO").getResultList();//.list();
        return attributes;
    }

}
