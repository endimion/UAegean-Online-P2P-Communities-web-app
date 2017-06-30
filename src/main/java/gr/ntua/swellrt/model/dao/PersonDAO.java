/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.model.dao;

import gr.ntua.swellrt.model.dmo.PersonSqlDMO;
import java.util.List;

/**
 *
 * @author nikos
 */
public interface PersonDAO {

    public List<PersonSqlDMO> findAll();

    public void saveEid(PersonSqlDMO eid);

    public PersonSqlDMO findByEid(String eid);

    public PersonSqlDMO findByToken(String token);
}
