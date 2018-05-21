/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.wrappersTest;

import teem.loginapp.controllers.RestControllers;
import teem.loginapp.model.dmo.AccountBuilder.SwellrtAccountMngDMO;
import teem.loginapp.model.dmo.StrokAttributesMongoDMO;
import teem.loginapp.pojo.AttributeTemplate;
import teem.loginapp.pojo.StorkResponse;
import teem.loginapp.service.MailService;
import teem.loginapp.service.StorkAttributeService;
import teem.loginapp.service.SwellrtAccountService;
import teem.loginapp.utils.AppUtils;
import teem.loginapp.utils.Wrappers;
import static teem.loginapp.utils.Wrappers.getAqaaLevel;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.JDOMException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class TestWrappers {

    @Configuration
    static class SaveJSONPOSTConfig {

        @Bean
        public StorkAttributeService attrServ() {
            return Mockito.mock(StorkAttributeService.class);
        }

        @Bean
        public MailService mailserv() {
            return Mockito.mock(MailService.class);
        }

        @Bean
        public SwellrtAccountService accountService() {
            return Mockito.mock(SwellrtAccountService.class);
        }

        @Bean
        public RestControllers restControllers() {
            return new RestControllers();
        }

        @Bean
        public CacheManager cacheManager() {
            return new CacheManager() {
                @Override
                public Cache getCache(String name) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public Collection<String> getCacheNames() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
        }
    }

    @Autowired
    private RestControllers restControllers;

    @Autowired
    private SwellrtAccountService accountService;

    @Autowired
    private MailService mailserv;

    @Autowired
    private StorkAttributeService attrserv;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testGetAqaaLevel() {
        try {
            String test = "<t><course>STORK101</course>"
                    + "<programmeOfStudy>programmeOfStudy</programmeOfStudy><AQAA>2</AQAA>"
                    + "<nameOfInstitution>nameOfInstitution</nameOfInstitution></t>";

            assertEquals("2", getAqaaLevel(test));

            test = "<s><t><course>STORK101</course>"
                    + "<programmeOfStudy>programmeOfStudy</programmeOfStudy><AQAA>2</AQAA>"
                    + "<nameOfInstitution>nameOfInstitution</nameOfInstitution></t></s>";

            assertEquals("2", getAqaaLevel(test));

        } catch (JDOMException ex) {
            Logger.getLogger(TestWrappers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestWrappers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    StrokAttributesMongoDMO givenNameAttr = new StrokAttributesMongoDMO();
    StrokAttributesMongoDMO familyNameAttr = new StrokAttributesMongoDMO();
    StrokAttributesMongoDMO dateOfBirthAttr = new StrokAttributesMongoDMO();
    StrokAttributesMongoDMO personIdentifierAttr = new StrokAttributesMongoDMO();

    @Before
    public void setup() {
        String curentGivenName = "http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName";
        String familyName = "http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName";
        String dateOfBirth = "http://eidas.europa.eu/attributes/naturalperson/DateOfBirth";
        String personIdentifier = "http://eidas.europa.eu/attributes/naturalperson/PersonIdentifier";

        givenNameAttr.setComplex(0);
        givenNameAttr.setEidasName(curentGivenName);
        givenNameAttr.setEnabled(true);
        givenNameAttr.setId("id1");
        givenNameAttr.setName("givenName");
        givenNameAttr.setRequestedLoa("low");
        givenNameAttr.setRequestedStorkQaa(1);
        givenNameAttr.setRequired(1);

        familyNameAttr.setComplex(0);
        familyNameAttr.setEidasName(familyName);
        familyNameAttr.setEnabled(true);
        familyNameAttr.setId("id1");
        familyNameAttr.setName("surname");
        familyNameAttr.setRequestedLoa("low");
        familyNameAttr.setRequestedStorkQaa(1);
        familyNameAttr.setRequired(1);

        dateOfBirthAttr.setComplex(0);
        dateOfBirthAttr.setEidasName(dateOfBirth);
        dateOfBirthAttr.setEnabled(true);
        dateOfBirthAttr.setId("id1");
        dateOfBirthAttr.setName("dateOfBirth");
        dateOfBirthAttr.setRequestedLoa("low");
        dateOfBirthAttr.setRequestedStorkQaa(1);
        dateOfBirthAttr.setRequired(1);

        personIdentifierAttr.setComplex(0);
        personIdentifierAttr.setEidasName(dateOfBirth);
        personIdentifierAttr.setEnabled(true);
        personIdentifierAttr.setId("id1");
        personIdentifierAttr.setName("eIdentifier");
        personIdentifierAttr.setRequestedLoa("low");
        personIdentifierAttr.setRequestedStorkQaa(1);
        personIdentifierAttr.setRequired(1);

        Mockito.when(attrserv.findByEiDASNameMng(curentGivenName))
                .thenReturn(givenNameAttr);
        Mockito.when(attrserv.findByEiDASNameMng(familyName))
                .thenReturn(familyNameAttr);
        Mockito.when(attrserv.findByEiDASNameMng(dateOfBirth))
                .thenReturn(dateOfBirthAttr);
        Mockito.when(attrserv.findByEiDASNameMng(personIdentifier))
                .thenReturn(personIdentifierAttr);
        
         Mockito.when(attrserv.findByNameMng("surname"))
                .thenReturn(familyNameAttr);

    }

    
    @Test
    public void testWrapStorkResponseToSwellrtAccountEIDASProperties() throws IOException {
        String responseString = "{\"http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName\":{\"value\":\"javier\",\"complex\":\"0\",\"required\":\"1\"},\"http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName\":{\"value\":\"Garcia\",\"complex\":\"0\",\"required\":\"1\"},\"http://eidas.europa.eu/attributes/naturalperson/DateOfBirth\":{\"value\":\"1965-01-01\",\"complex\":\"0\",\"required\":\"1\"},\"http://eidas.europa.eu/attributes/naturalperson/PersonIdentifier\":{\"value\":\"GR/GR/12345\",\"complex\":\"0\",\"required\":\"1\"}}";
        StorkResponse response = new StorkResponse();
        Map<String, AttributeTemplate> receivedValues = AppUtils.parseStorkJSONResponse(responseString);
        response.setReceivedAttributes(receivedValues);

        SwellrtAccountMngDMO receivedUser = Wrappers.wrapStorkResponseToSwellrtAccount(response, attrserv);
        assertEquals(receivedUser.getEid(), "GR/GR/12345");
        assertEquals(receivedUser.getAttributes().get("CurrentGivenName").getValue(), "javier");
    }

    @Test
    public void testWrapStorkResponseToSwellrtAccountSTORKProperties() throws IOException {
        String responseString = "{\"surname\":{\"value\":\"ΠΑΠΑΔΑΚΗΣ\",\"complex\":\"0\",\"required\":\"0\"},\"givenName\":{\"value\":\"ΧΑΡΑΛΑΜΠΟΣ\",\"complex\":\"0\",\"required\":\"0\"},\"dateOfBirth\":{\"value\":\"1977-05-29T00%5800%5800.000Z\",\"complex\":\"0\",\"required\":\"0\"},\"eIdentifier\":{\"value\":\"GR/GR/ERMIS-80942135\",\"complex\":\"0\",\"required\":\"0\"}}";
        StorkResponse response = new StorkResponse();
        Map<String, AttributeTemplate> receivedValues = AppUtils.parseStorkJSONResponse(responseString);

        response.setReceivedAttributes(receivedValues);
        SwellrtAccountMngDMO receivedUser = Wrappers.wrapStorkResponseToSwellrtAccount(response, attrserv);

        assertEquals(receivedUser.getEid(), "GR/GR/ERMIS-80942135");
        assertEquals(receivedUser.getAttributes().get("CurrentFamilyName").getValue(), "ΠΑΠΑΔΑΚΗΣ");
        assertEquals(receivedUser.getAttributes().get("surname"), null);
    }
}
