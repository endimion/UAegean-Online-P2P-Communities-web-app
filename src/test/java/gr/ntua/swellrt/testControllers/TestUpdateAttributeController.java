/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.testControllers;

import gr.ntua.swellrt.controllers.RestControllers;
import gr.ntua.swellrt.model.dmo.AccountBuilder;
import gr.ntua.swellrt.pojo.ReceivedStorkAttribute;
import gr.ntua.swellrt.service.MailService;
import gr.ntua.swellrt.service.StorkAttributeService;
import gr.ntua.swellrt.service.SwellrtAccountService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.Cookie;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class TestUpdateAttributeController {

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
    public void testWhenCookiesAreMissing() throws Exception {
        assertEquals(true, true);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.restControllers).build();
        mockMvc.perform(put("/updateAttribute/UAgeanID/ABX/121232134/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("FAIL")));

    }

    @Test
    public void testWhenWithCookies() throws Exception {
        assertEquals(true, true);
        Cookie c = new Cookie("eId", "testEid");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.restControllers).build();
        mockMvc.perform(put("/updateAttribute/UAgeanID/ABX/121232134/").cookie(c))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")));

    }

    @Test
    public void testWhenWithCookiesBadUAegeanID() throws Exception {
        assertEquals(true, true);
        Cookie c = new Cookie("eId", "testEid");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.restControllers).build();
        mockMvc.perform(put("/updateAttribute/UAgeanID/ABX121232134/").cookie(c))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("FAIL")));

    }

    @Test
    public void testWhenWithCookiesAddEmil() throws Exception {
        assertEquals(true, true);
        Cookie c = new Cookie("eId", "testEid");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.restControllers).build();
        mockMvc.perform(put("/updateAttribute/email/me@me.gr/").cookie(c))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")));

    }
}
