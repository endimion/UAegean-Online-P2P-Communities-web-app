/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.model.impl;

import teem.loginapp.model.dao.PersonDAO;
import teem.loginapp.model.dmo.PersonSqlDMO;
import java.util.List;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author nikos
 */
@Repository
public class EIdDAOImpl implements PersonDAO {

//    @Autowired
//    private SessionFactory sessionFactory;
   
    @PersistenceContext
    public EntityManager em;

    private final static Logger logger = LoggerFactory.getLogger(EIdDAOImpl.class);

    @Override
    public List<PersonSqlDMO> findAll() {
//        Session session = this.sessionFactory.getCurrentSession();
        
        List<PersonSqlDMO> personsList = em.createQuery(" from PersonSqlDMO").getResultList();
//        List<PersonSqlDMO> personsList = session.createQuery(" from PersonSqlDMO").list();
        for (PersonSqlDMO p : personsList) {
            //Forces the initialization of the collection object returned by getUsers()
            Hibernate.initialize(p.getAttributesValues());
        }
        return personsList;
    }

    @Override
    public void saveEid(PersonSqlDMO eid) {
        //Session session = this.sessionFactory.getCurrentSession();
        em.merge(eid);
        // session.merge(eid);
    }

    @Override
    public PersonSqlDMO findByEid(String eid) {
        //Session session = this.sessionFactory.getCurrentSession();
        PersonSqlDMO result = (PersonSqlDMO) em.createQuery("SELECT e FROM PersonSqlDMO e where eid = :eid")
                .setParameter("eid", eid)
                .getSingleResult();

        if (result != null) {
            Hibernate.initialize(result.getAttributesValues());
            result.getAttributesValues().forEach(atrval -> {
                Hibernate.initialize(atrval.getAttribute());
            });
        }
        return result;
    }

    @Override
    public PersonSqlDMO findByToken(String token) {
        //Session session = this.sessionFactory.getCurrentSession();
        PersonSqlDMO result = (PersonSqlDMO) em.createQuery("SELECT e FROM PersonSqlDMO e where token = :token")
                .setParameter("token", token)
                .getSingleResult();
        if (result != null) {
            Hibernate.initialize(result.getAttributesValues());
            result.getAttributesValues().forEach(atrval -> {
                Hibernate.initialize(atrval.getAttribute());
            });
        }

        return result;
    }

}
