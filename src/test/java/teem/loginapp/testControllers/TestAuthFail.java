/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.testControllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import teem.loginapp.controllers.RestControllers;
import teem.loginapp.model.dmo.AccountBuilder;
import teem.loginapp.pojo.ReceivedStorkAttribute;
import teem.loginapp.service.MailService;
import teem.loginapp.service.StorkAttributeService;
import teem.loginapp.service.SwellrtAccountService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import teem.loginapp.controllers.Controllers;
import teem.loginapp.service.CountriesService;
import teem.loginapp.service.PropertiesService;
import teem.loginapp.service.TeemProjectService;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@WebMvcTest
public class TestAuthFail {

    @Configuration
    static class UpdateAttributeTestConfiguration {

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
        public Controllers Controllers() {
            return new Controllers();
        }

        @Bean
        public TeemProjectService teemProjectService() {
            return Mockito.mock(TeemProjectService.class);
        }
        
        @Bean
        public CountriesService countriesService() {
            return Mockito.mock(CountriesService.class);
        }
        

        @Bean
        public PropertiesService propertiesService() {
            return Mockito.mock(PropertiesService.class);
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
    private Controllers viewControllers;

    @Autowired
    private SwellrtAccountService accountService;

    @Autowired
    private MailService mailserv;

    @Autowired
    private StorkAttributeService attrserv;

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
        AccountBuilder.SwellrtAccountMngDMO account = builder.build();

        //accountService
        Mockito.when(accountService.findByEid("testEid")).thenReturn(account);
    }

    @Test
    public void testAuthSuccessRedirectFail() throws Exception {
        assertEquals(true, true);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.viewControllers).build();
        mockMvc.perform(get("/authsuccess").param("t", "test"))
                .andExpect(redirectedUrl("/authfail?t=err"));
//                .andExpect(model().attributeExists("errorMsg"));

    }

}
