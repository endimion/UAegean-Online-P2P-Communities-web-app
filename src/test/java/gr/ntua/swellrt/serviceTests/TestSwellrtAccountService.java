/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.serviceTests;

import gr.ntua.swellrt.model.dao.SwellrtAccountRepository;
import gr.ntua.swellrt.model.dmo.AccountBuilder;
import gr.ntua.swellrt.model.dmo.AccountBuilder.SwellrtAccountMngDMO;
import gr.ntua.swellrt.pojo.ReceivedStorkAttribute;
import gr.ntua.swellrt.service.StorkAttributeService;
import gr.ntua.swellrt.service.SwellrtAccountService;
import gr.ntua.swellrt.serviceImpl.StorkAttributeServiceImpl;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestSwellrtAccountService {

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public StorkAttributeService accountService() {
            return new StorkAttributeServiceImpl();
        }

        @Bean
        public SwellrtAccountRepository accountRepository() {
            return Mockito.mock(SwellrtAccountRepository.class);
        }
        
    }

    //We Autowired the AccountService bean so that it is injected from the configuration
    @Autowired
    private SwellrtAccountService accountService;
    @Autowired
    private SwellrtAccountRepository accountRepository;

    @Before
    public void setup() {

        List<ReceivedStorkAttribute> attributes = new ArrayList();

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
        builder.setAttributes(attributes);
        SwellrtAccountMngDMO account = builder.build();

        List<ReceivedStorkAttribute> attributes2 = new ArrayList();
        ReceivedStorkAttribute familyName = new ReceivedStorkAttribute();
        familyName.setLoa("loa");
        familyName.setRequestedLoA("reqLoa");
        familyName.setStorkName("familyName");
        familyName.setValue("theFamilyName");
        familyName.setaQAA("aQaa");
        familyName.seteIDASName("FamilyName");

        ReceivedStorkAttribute dateOfBirth = new ReceivedStorkAttribute();
        dateOfBirth.setLoa("loa");
        dateOfBirth.setRequestedLoA("reqLoa");
        dateOfBirth.setStorkName("dateOfBirth");
        dateOfBirth.setValue("DateOfBirth");
        dateOfBirth.setaQAA("aQaa");
        dateOfBirth.seteIDASName("DateOfBirth");

        ReceivedStorkAttribute birthName = new ReceivedStorkAttribute();
        birthName.setLoa("loa");
        birthName.setRequestedLoA("reqLoa");
        birthName.setStorkName("birthName");
        birthName.setValue("BirthName");
        birthName.setaQAA("aQaa");
        birthName.seteIDASName("BirthName");

        ReceivedStorkAttribute currentAddress = new ReceivedStorkAttribute();
        currentAddress.setLoa("loa");
        currentAddress.setRequestedLoA("reqLoa");
        currentAddress.setStorkName("currentAddress");
        currentAddress.setValue("CurrentAddress");
        currentAddress.setaQAA("aQaa");
        currentAddress.seteIDASName("CurrentAddress");
        attributes2.add(currentAddress);

        attributes2.add(birthName);
        attributes2.add(familyName);
        attributes2.add(dateOfBirth);

        AccountBuilder builder2 = new AccountBuilder();
        builder2.setEid("testEid2");
        builder2.setEmail("me@me.gr");
        builder2.setId("testId");
        builder2.setLocalPassword("loclPassword");
        builder2.setLocale("en");
        builder2.setOpenPassword("openPassword");
        builder2.setPassword("password");
        builder2.setTimestamp("timestamp");
        builder2.setToken("token");
        builder2.setUsername("username");
        builder2.setAttributes(attributes2);
        SwellrtAccountMngDMO accountWithAttributes = builder2.build();

        Mockito.when(accountRepository.findByEid("testEid")).thenReturn(account);
        Mockito.when(accountRepository.findByEid("testEid2")).thenReturn(accountWithAttributes);
    }

    @Test
    public void testAddNewAttribute() {

        List<ReceivedStorkAttribute> attributes = new ArrayList();
        ReceivedStorkAttribute familyName = new ReceivedStorkAttribute();
        familyName.setLoa("loa");
        familyName.setRequestedLoA("reqLoa");
        familyName.setStorkName("familyName");
        familyName.setValue("theFamilyName");
        familyName.setaQAA("aQaa");
        familyName.seteIDASName("FamilyName");
        attributes.add(familyName);

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
        builder.setAttributes(attributes);
        SwellrtAccountMngDMO account = builder.build();

        accountService.saveOrUpdateTeemAttributes(account);

        assertEquals(account.getAttributes().containsKey("FamilyName"), true);
        assertEquals(account.getAttributes().containsKey("DateOfBirth"), false);
    }

    @Test
    public void testAddAegeanID() {

        List<ReceivedStorkAttribute> attributes = new ArrayList();
        ReceivedStorkAttribute aegeanID = new ReceivedStorkAttribute();
        aegeanID.setLoa("loa");
        aegeanID.setRequestedLoA("reqLoa");
        aegeanID.setStorkName("UAegeanID");
        aegeanID.setValue("abc/123");
        aegeanID.setaQAA("aQaa");
        aegeanID.seteIDASName("UAegeanID");
        attributes.add(aegeanID);

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
        builder.setAttributes(attributes);
        SwellrtAccountMngDMO account = builder.build();

        accountService.saveOrUpdateTeemAttributes(account);

        assertEquals(account.getAttributes().containsKey("UAegeanID"), true);
    }

    @Test
    public void testSaveTeemDoNotOverwriteValues() {

        List<ReceivedStorkAttribute> attributes = new ArrayList();
        ReceivedStorkAttribute familyName = new ReceivedStorkAttribute();
        familyName.setLoa("loa");
        familyName.setRequestedLoA("reqLoa");
        familyName.setStorkName("familyName");
        familyName.setValue("NewtheFamilyName");
        familyName.setaQAA("aQaa");
        familyName.seteIDASName("FamilyName");
        attributes.add(familyName);

        AccountBuilder builder = new AccountBuilder();
        builder.setEid("testEid2");
        builder.setEmail("me@me.gr");
        builder.setId("testId");
        builder.setLocalPassword("loclPassword");
        builder.setLocale("en");
        builder.setOpenPassword("openPassword");
        builder.setPassword("password");
        builder.setTimestamp("timestamp");
        builder.setToken("token");
        builder.setUsername("username");
        builder.setAttributes(attributes);
        SwellrtAccountMngDMO account = builder.build();

        accountService.saveOrUpdateTeemAttributes(account);

        assertEquals(account.getAttributes().containsKey("FamilyName"), true);
        assertEquals(account.getAttributes().get("FamilyName").getValue(), "theFamilyName");
    }

    @Test
    public void testSaveDoNotOverwriteValues() {

        List<ReceivedStorkAttribute> attributes = new ArrayList();
        ReceivedStorkAttribute familyName = new ReceivedStorkAttribute();
        familyName.setLoa("loa");
        familyName.setRequestedLoA("reqLoa");
        familyName.setStorkName("familyName");
        familyName.setValue("NewtheFamilyName");
        familyName.setaQAA("aQaa");
        familyName.seteIDASName("FamilyName");
        attributes.add(familyName);

        ReceivedStorkAttribute currentAddress = new ReceivedStorkAttribute();
        currentAddress.setLoa("loa");
        currentAddress.setRequestedLoA("reqLoa");
        currentAddress.setStorkName("currentAddress");
        currentAddress.setValue("NewCurrentAddress");
        currentAddress.setaQAA("aQaa");
        currentAddress.seteIDASName("CurrentAddress");
        attributes.add(currentAddress);

        AccountBuilder builder = new AccountBuilder();
        builder.setEid("testEid2");
        builder.setEmail("me@me.gr");
        builder.setId("testId");
        builder.setLocalPassword("loclPassword");
        builder.setLocale("en");
        builder.setOpenPassword("openPassword");
        builder.setPassword("password");
        builder.setTimestamp("timestamp");
        builder.setToken("token");
        builder.setUsername("username");
        builder.setAttributes(attributes);
        SwellrtAccountMngDMO account = builder.build();

        accountService.saveOrUpdate(account);

        assertEquals(account.getAttributes().containsKey("FamilyName"), true);
        assertEquals(account.getAttributes().get("FamilyName").getValue(), "NewtheFamilyName");
        assertEquals(account.getAttributes().get("CurrentAddress").getValue(), "CurrentAddress");
    }

}
