/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.wrappersTest;

import teem.loginapp.model.dmo.AccountBuilder;
import teem.loginapp.model.dmo.AccountBuilder.SwellrtAccountMngDMO;
import teem.loginapp.model.dmo.StrokAttributesMongoDMO;
import teem.loginapp.pojo.ReceivedStorkAttribute;
import teem.loginapp.pojo.UserCredentials;
import teem.loginapp.utils.Wrappers;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestWrapSwellrtAccountToUserCredentials {

    List<StrokAttributesMongoDMO> enabledAttributes = new ArrayList();
    SwellrtAccountMngDMO account = new AccountBuilder.SwellrtAccountMngDMO();

    @Before
    public void setup() {
        StrokAttributesMongoDMO attr1 = new StrokAttributesMongoDMO();
        attr1.setComplex(0);
        attr1.setEidasName("UAgeanID");
        attr1.setEnabled(true);
        attr1.setId("at1");
        attr1.setName("UAgeanID");
        attr1.setRequestedLoa("reqLoa");
        attr1.setRequestedStorkQaa(0);
        attr1.setRequired(1);
        enabledAttributes.add(attr1);

        List<ReceivedStorkAttribute> accountAttributes = new ArrayList();

        AccountBuilder builder = new AccountBuilder();
        builder.setEid("testEid");
        builder.setEmail("me@me.gr");
        builder.setId("testId");
        builder.setLocalPassword("loclPassword");
        builder.setLocale("en");
        builder.setOpenPassword("openPassword");
        builder.setPassword("password");
        builder.setTimestamp("timestamp");
        builder.setToken("token");
        builder.setUsername("username");
        builder.setAttributes(accountAttributes);
        account = builder.build();
    }

    @Test
    public void testCredentialsGetAttributeIfNotSet() {
         UserCredentials creds = Wrappers.wrapSwellrtAccounttoUserCredentials(account, enabledAttributes);
         assertEquals(creds.getAttributes().containsKey("UAgeanID"),true);
    }

    @Test
    public void testCredentialsGetAttributeIfSet() {
        
        ReceivedStorkAttribute attr = new ReceivedStorkAttribute();
        attr.setLoa("loa");
        attr.setRequestedLoA("reqLoa");
        attr.setRequestedStorkQAA("qaa");
        attr.setStorkName("UAgeanID");
        attr.setValue("abc/123");
        attr.setaQAA("aqaa");
        attr.seteIDASName("UAgeanID");
        
        account.getAttributes().put("UAgeanID", attr);
        
         UserCredentials creds = Wrappers.wrapSwellrtAccounttoUserCredentials(account, enabledAttributes);
         assertEquals(creds.getAttributes().containsKey("UAgeanID"),true);
         assertEquals(creds.getAttributes().get("UAgeanID").getValue(),"abc/123");
    }
    
    
    
    
}
