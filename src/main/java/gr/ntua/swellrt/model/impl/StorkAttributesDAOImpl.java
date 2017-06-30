/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.model.impl;

import gr.ntua.swellrt.model.dao.StorkAttributesDAO;
import gr.ntua.swellrt.model.dmo.StorkAttributesDMO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;


/**
 *
 * @author nikos
 */
@Repository
public class StorkAttributesDAOImpl implements StorkAttributesDAO {

//    @Autowired
//    private SessionFactory sessionFactory;
    
     @PersistenceContext
    public EntityManager em;
    private final static Logger logger = LoggerFactory.getLogger(StorkAttributesDAOImpl.class);

    @Override
    public List<StorkAttributesDMO> findAll() {
        //Session session = this.sessionFactory.getCurrentSession();
        List<StorkAttributesDMO> result = em.createQuery(" from StorkAttributesDMO").getResultList();
        return result;
    }

    @Override
    public StorkAttributesDMO findByName(String attributeName) {
        //ssion session = this.sessionFactory.getCurrentSession();
        StorkAttributesDMO result = 
                (StorkAttributesDMO) em.createQuery(" from StorkAttributesDMO where name = :name")
                .setParameter("name", attributeName)
                .getSingleResult();
        return result;
    }

    @Override
    public List<StorkAttributesDMO> getEnabledAttributes() {
        //Session session = this.sessionFactory.getCurrentSession();
        return em.
                createQuery(" from StorkAttributesDMO where enabled = :enabled")
                .setParameter("enabled", true)
                .getResultList();
    }

}
