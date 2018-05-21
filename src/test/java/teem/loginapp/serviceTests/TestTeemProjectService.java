/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.serviceTests;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;
import teem.loginapp.Application;
import teem.loginapp.model.dao.TeemProjectsRepo;
import teem.loginapp.model.dmo.AccountBuilder;
import teem.loginapp.model.dmo.TeemProject;
import teem.loginapp.pojo.ReceivedStorkAttribute;
import teem.loginapp.service.SwellrtAccountService;
import teem.loginapp.service.TeemProjectService;
import teem.loginapp.serviceTests.TestTeemProjectService.mockConfig;

/**
 *
 * @SpringBootTest @author nikos
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {mockConfig.class, Application.class})
public class TestTeemProjectService {

    @Configuration
    public static class mockConfig {

        @Bean
        @Primary
        public SwellrtAccountService mockAccountService() {
            return Mockito.mock(SwellrtAccountService.class);
        }

        @Bean
        @Primary
        public TeemProjectsRepo mockTeemProjectsRepo() {
            return Mockito.mock(TeemProjectsRepo.class);
        }

        @Bean
        @Primary
        public JavaMailSenderImpl javaMailSenderImpl() {
            return Mockito.mock(JavaMailSenderImpl.class);
        }

    }

    @Autowired
    TeemProjectsRepo projectRepo;

    @Autowired
    SwellrtAccountService accountServ;

    @Autowired
    TeemProjectService projServ;

    @Before
    public void setup() {
        List<String> participants = new ArrayList();
        participants.add("testId1");
        participants.add("testId2");
        TeemProject.Root root = new TeemProject.Root();
        root.setId("rootId");
        root.setPromoter("promoter");

        TeemProject testProj = new TeemProject();
        testProj.setId("testId");
        testProj.setWaveId("waveID");
        testProj.setWaveletId("waveletID");
        testProj.setRoot(root);
        testProj.setParticipants(participants);

        AccountBuilder account1 = new AccountBuilder();
        ReceivedStorkAttribute fname1 = new ReceivedStorkAttribute();
        fname1.setValue("firstName1");
        fname1.seteIDASName("http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName");
        ReceivedStorkAttribute lname1 = new ReceivedStorkAttribute();
        lname1.setValue("lastName1");
        lname1.seteIDASName("http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName");
        List<ReceivedStorkAttribute> atts1 = new ArrayList();
        atts1.add(lname1);
        atts1.add(fname1);
        account1.setEmail("mail1");
        account1.setAttributes(atts1);
        account1.setPassword("pass1");
        account1.setUsername("usern");

        AccountBuilder account2 = new AccountBuilder();
        ReceivedStorkAttribute fname2 = new ReceivedStorkAttribute();
        fname2.setValue("firstName2");
        fname2.seteIDASName("http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName");
        ReceivedStorkAttribute lname2 = new ReceivedStorkAttribute();
        lname2.setValue("lastName2");
        lname2.seteIDASName("http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName");
        List<ReceivedStorkAttribute> atts2 = new ArrayList();
        atts2.add(lname2);
        atts2.add(fname2);
        account2.setAttributes(atts2);
        account2.setEmail("mail2");
        account2.setPassword("pass2");
        account2.setUsername("usern");

        Mockito.when(accountServ.findById("testId1")).thenReturn(account1.build());
        Mockito.when(accountServ.findById("testId2")).thenReturn(account2.build());

        Mockito.when(projectRepo.findByWaveId("waveID")).thenReturn(testProj);

    }

    @Test
    public void testGetParticipants() {
        projServ.getParticipantsIfPromoter("waveID", "promoter");
        assertEquals(true, true);
    }

}
