/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.testControllers;

import teem.loginapp.controllers.RestControllers;
import teem.loginapp.model.dmo.TeemProject;
import teem.loginapp.pojo.SwellrtEvent;
import teem.loginapp.service.MailService;
import teem.loginapp.service.StorkAttributeService;
import teem.loginapp.service.SwellrtAccountService;
import teem.loginapp.service.TeemProjectService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class TestReceiveEventController {

    @Configuration
    static class ReceiveEventConfiguration {

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
        public TeemProjectService projectServ(){
            return Mockito.mock(TeemProjectService.class);
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
    private TeemProjectService projServ;

    @Before
    public void setup() {
        Mockito.when(mailserv.sendEmailsForEvent(any(SwellrtEvent.class),any(List.class)))
                .thenReturn("OK");
        
        TeemProject  proj = new TeemProject();
        proj.setId("id");
        proj.setParticipants(new ArrayList());
        proj.setWaveId("waveid");
        proj.setWaveletId("weabelt");
        
        Mockito.when(projServ.findByWave_id(any(String.class)))
                .thenReturn(proj);
    }

    @Test
    public void testReceiveEventNoRecipients() throws Exception {
        String jsonInString = "{\"path\":\"root.needs.12.completionDate\",\"data\":{\"projId\":\"local.net/s+hqwFSt0sybA\",\"summaryText\":\"There are %n% notifications\",\"collapseKey\":\"applice\",\"context\":\"needs\",\"style\":\"inbox\",\"title\":\"Jewelry\",\"message\":\"✔ a task 9\",\"notId\":\"889303311\"},\"waveid\":\"local.net/s+hqwFSt0sybA\"} ";

        assertEquals(true, true);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.restControllers).build();
        MvcResult result = mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInString)
                .header("token", "95901e5b61fd7c4f5f952927347f0994d0e22a3166bf7c90fb0287e8b87058fa"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(), "");
    }

    @Test
    public void testReceiveEventWithRecipients() throws Exception {
        String jsonInString = "{\"path\":\"root.needs.12.completionDate\",  \"data\":{\"recipients\": [\"test@test.gr\",\"test2@test.gr\"],\"projId\":\"local.net/s+hqwFSt0sybA\",\"summaryText\":\"There are %n% notifications\",\"collapseKey\":\"applice\",\"context\":\"needs\",\"style\":\"inbox\",\"title\":\"Jewelry\",\"message\":\"✔ a task 9\",\"notId\":\"889303311\"},\"waveid\":\"local.net/s+hqwFSt0sybA\"} ";

        assertEquals(true, true);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.restControllers).build();
        MvcResult asyncResult = mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInString)
                .header("token", "95901e5b61fd7c4f5f952927347f0994d0e22a3166bf7c90fb0287e8b87058fa"))
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(instanceOf(String.class)))
                .andReturn();

        MvcResult result = mockMvc.perform(asyncDispatch(asyncResult))
                .andExpect(status().isOk()).andReturn();

        assertEquals(result.getResponse().getContentAsString(), "OK");
    }
    
    
    
     @Test
    public void testReceiveEventWithRecipientsNoHeaderToken() throws Exception {
        String jsonInString = "{\"path\":\"root.needs.12.completionDate\",  \"data\":{\"recipients\": [\"test@test.gr\",\"test2@test.gr\"],\"projId\":\"local.net/s+hqwFSt0sybA\",\"summaryText\":\"There are %n% notifications\",\"collapseKey\":\"applice\",\"context\":\"needs\",\"style\":\"inbox\",\"title\":\"Jewelry\",\"message\":\"✔ a task 9\",\"notId\":\"889303311\"},\"waveid\":\"local.net/s+hqwFSt0sybA\"} ";

        assertEquals(true, true);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.restControllers).build();
        MvcResult asyncResult = mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInString)
                .header("token", "123"))
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(instanceOf(String.class)))
                .andReturn();

        MvcResult result = mockMvc.perform(asyncDispatch(asyncResult))
                .andExpect(status().isOk()).andReturn();

        assertEquals(result.getResponse().getContentAsString(), "Invalid token");
    }
    
    
     @Test
    public void testReceiveEventWithRecipientsWrongHeaderToken() throws Exception {
        String jsonInString = "{\"path\":\"root.needs.12.completionDate\",  \"data\":{\"recipients\": [\"test@test.gr\",\"test2@test.gr\"],\"projId\":\"local.net/s+hqwFSt0sybA\",\"summaryText\":\"There are %n% notifications\",\"collapseKey\":\"applice\",\"context\":\"needs\",\"style\":\"inbox\",\"title\":\"Jewelry\",\"message\":\"✔ a task 9\",\"notId\":\"889303311\"},\"waveid\":\"local.net/s+hqwFSt0sybA\"} ";

        assertEquals(true, true);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.restControllers).build();
        MvcResult asyncResult = mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInString))
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(instanceOf(String.class)))
                .andReturn();

        MvcResult result = mockMvc.perform(asyncDispatch(asyncResult))
                .andExpect(status().isOk()).andReturn();

        assertEquals(result.getResponse().getContentAsString(), "Invalid token");
    }
    
    
    

    @Test
    public void testReceiveEventErrorData() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.restControllers).build();
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("test"))
                .andExpect(status().is(400));
    }

}
