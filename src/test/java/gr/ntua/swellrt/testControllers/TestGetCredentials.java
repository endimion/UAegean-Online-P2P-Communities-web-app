/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.testControllers;

import gr.ntua.swellrt.controllers.RestControllers;
import gr.ntua.swellrt.pojo.SwellrtEvent;
import gr.ntua.swellrt.service.MailService;
import gr.ntua.swellrt.service.StorkAttributeService;
import gr.ntua.swellrt.service.SwellrtAccountService;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class TestGetCredentials {

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

    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    ValueWrapper mockWrapper;
    
    @Before
    public void setup() {
        Mockito.when(mailserv.sendEmailsForEvent(any(SwellrtEvent.class)))
                .thenReturn("OK");
        mockWrapper = Mockito.mock(ValueWrapper.class);
        request.addHeader(HttpHeaders.HOST, "myhost.com");
//        request.setLocalPort(PORT_VALID); // e.g. 8081
        request.setRemoteAddr("127.0.0.1"); // e.g. 127.0.0.1
        Mockito.when(cacheManager.getCache("ips").get(request.getRemoteAddr())).thenReturn(mockWrapper);
        
    }

    
    private final static String token = "53f9dc9a-b1fd-4fe5-9484-a4a16ccfd3c5";

    @Test
    public void testAccountFound() {
        Mockito.when(accountService.findByToken(token)).thenReturn(null);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.restControllers).build();

    }

}
