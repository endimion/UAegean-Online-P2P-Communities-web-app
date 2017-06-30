/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt;

import com.mongodb.MongoClient;
import gr.ntua.swellrt.model.dmo.PersonSqlDMO;
import gr.ntua.swellrt.model.dmo.StorkAttributeValuesDMO;
import gr.ntua.swellrt.model.dmo.StorkAttributesDMO;
import gr.ntua.swellrt.utils.MongoUtils;
import gr.ntua.swellrt.utils.PasswordDigest;
import java.nio.charset.Charset;
import java.util.HashSet;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestMongo {

//    @Test
    public void testPassDigestCreation() {
        PersonSqlDMO user = new PersonSqlDMO();
        user.setUsername("nikos1123");
        user.setEmail("email@test.gr");
        user.setPassword("password");
        StorkAttributesDMO attr = new StorkAttributesDMO();
        attr.setName("atgtr");
        attr.setEidasName("eidasName");

        StorkAttributeValuesDMO attrv = new StorkAttributeValuesDMO();
        attrv.setAttribute(attr);
        attrv.setValue("test value");

        user.setAttributesValues(new HashSet<StorkAttributeValuesDMO>());
        user.getAttributesValues().add(attrv);
        MongoClient mongo = new MongoClient();
        MongoUtils.saveUser(user);
        mongo.close();

    }

    @Test
    public void testSaveOrUpdateUser() {
        PersonSqlDMO user = new PersonSqlDMO();
        user.setUsername("nikos");
        user.setEmail("email@test.gr");
        user.setPassword("password");
        StorkAttributesDMO attr = new StorkAttributesDMO();
        attr.setName("atgtr");
        attr.setEidasName("eidasName");

        StorkAttributeValuesDMO attrv = new StorkAttributeValuesDMO();
        attrv.setAttribute(attr);
        attrv.setValue("test value");

        user.setAttributesValues(new HashSet<StorkAttributeValuesDMO>());
        user.getAttributesValues().add(attrv);
        MongoClient mongo = new MongoClient();
        MongoUtils.saveOrUpdate(user);
        mongo.close();
    }
}
