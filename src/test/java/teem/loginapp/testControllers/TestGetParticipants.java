/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.testControllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import teem.loginapp.Application;
import teem.loginapp.controllers.RestControllers;
import teem.loginapp.model.dao.TeemProjectsRepo;
import teem.loginapp.model.dmo.AccountBuilder;
import teem.loginapp.pojo.SwellrtEvent;
import teem.loginapp.service.MailService;
import teem.loginapp.service.StorkAttributeService;
import teem.loginapp.service.SwellrtAccountService;
import teem.loginapp.service.TeemProjectService;
import teem.loginapp.testControllers.TestGetParticipants.mockConfig;

/**
 *
 * @author nikos
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {mockConfig.class, Application.class})
public class TestGetParticipants {

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

        @Bean
        @Primary
        public StorkAttributeService attrServ() {
            return Mockito.mock(StorkAttributeService.class);
        }

        @Bean
        @Primary
        public MailService mailserv() {
            return Mockito.mock(MailService.class);
        }

        @Bean
        @Primary
        public TeemProjectService projectServ() {
            return Mockito.mock(TeemProjectService.class);
        }

        @Bean
        @Primary
        public RestControllers restControllers() {
            return new RestControllers();
        }

        @Bean
        @Primary
        public CacheManager cacheManager() {
            return new CacheManager() {
                @Override
                public Cache getCache(String name) {
                    return Mockito.mock(Cache.class);
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
    private SwellrtAccountService mockAccountService;

    @Autowired
    private MailService mailserv;

    @Autowired
    private StorkAttributeService attrserv;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private TeemProjectService projServ;

    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    Cache.ValueWrapper mockWrapper;

    @Before
    public void setup() {
        Mockito.when(mailserv.sendEmailsForEvent(any(SwellrtEvent.class), any(List.class)))
                .thenReturn("OK");
        mockWrapper = Mockito.mock(Cache.ValueWrapper.class);
        request.addHeader(HttpHeaders.HOST, "myhost.com");
        request.setRemoteAddr("127.0.0.1"); // e.g. 127.0.0.1
        Mockito.when(cacheManager.getCache("ips").get(any(String.class))).thenReturn(mockWrapper);

        String[] part1 = new String[3];
        part1[0] = "fname1";
        part1[1] = "lname1";
        part1[2] = "mail1";
        String[] part2 = new String[3];
        part2[0] = "fname1";
        part2[1] = "lname1";
        part2[2] = "mail1";

        String[][] participants = new String[100][100];
        participants[0] =part2;
        participants[1] = part1;
        AccountBuilder account2 = new AccountBuilder();
        account2.setId("promoterId");
        account2.setAttributes(new ArrayList());
        account2.setEmail("mail2");
        account2.setPassword("pass2");
        account2.setUsername("usern");

        Mockito.when(mockAccountService.findById(any(String.class))).thenReturn(account2.build());
        Mockito.when(mockAccountService.findByToken(any(String.class))).thenReturn(account2.build());
        Mockito.when(projServ.getParticipantsIfPromoter(any(String.class), any(String.class))).thenReturn(participants);

    }

    private final static String token = "53f9dc9a-b1fd-4fe5-9484-a4a16ccfd3c5";

    @Test
    public void testAccountFound() throws Exception {
        mockAccountService.findById("promoterId");
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.restControllers).build();
        System.out.println(mockMvc.perform(get("/getParticipants").param("token", token)
                .param("projectId", "projectId"))
                .andExpect(status().isOk())
                .andReturn()
                .getAsyncResult(200000000) instanceof String[][]);
        assertEquals(true, true);
    }

}
