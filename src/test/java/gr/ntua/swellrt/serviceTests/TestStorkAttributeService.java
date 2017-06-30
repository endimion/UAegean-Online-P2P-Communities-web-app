/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.serviceTests;

import gr.ntua.swellrt.model.dao.StorkAttributeMngRepository;
import gr.ntua.swellrt.model.dao.StorkAttributesDAO;
import gr.ntua.swellrt.model.dao.SwellrtAccountRepository;
import gr.ntua.swellrt.model.dmo.StrokAttributesMongoDMO;
import gr.ntua.swellrt.service.StorkAttributeService;
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
public class TestStorkAttributeService {

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public StorkAttributeService accountService() {
            return new StorkAttributeServiceImpl();
        }

        @Bean
        public StorkAttributeMngRepository accountRepository() {
            return Mockito.mock(StorkAttributeMngRepository.class);
        }

        @Bean
        public StorkAttributesDAO storkAttributesDAO() {
            return Mockito.mock(StorkAttributesDAO.class);
        }
    }

    @Autowired
    private StorkAttributeService attrService;
    @Autowired
    private StorkAttributeMngRepository attRepo;

    @Before
    public void setup() {
        List<StrokAttributesMongoDMO> attributes = new ArrayList();
        StrokAttributesMongoDMO attr1 = new StrokAttributesMongoDMO();
        attr1.setComplex(0);
        attr1.setEidasName("eidasName");
        attr1.setEnabled(true);
        attr1.setId("at1");
        attr1.setName("attribute1");
        attr1.setRequestedLoa("reqLoa");
        attr1.setRequestedStorkQaa(0);
        attr1.setRequired(1);

        StrokAttributesMongoDMO attr2 = new StrokAttributesMongoDMO();
        attr2.setComplex(0);
        attr2.setEidasName("eidasName");
        attr2.setEnabled(false);
        attr2.setId("at1");
        attr2.setName("attribute2");
        attr2.setRequestedLoa("reqLoa");
        attr2.setRequestedStorkQaa(0);
        attr2.setRequired(1);

        attributes.add(attr1);
        attributes.add(attr2);

        Mockito.when(attRepo.findAll()).thenReturn(attributes);

    }

    @Test
    public void testGetEnabledMng() {
        assertEquals(attrService.getEnabledMng().size(), 1);
        assertEquals(attrService.getEnabledMng().get(0).getName(),"attribute1");
    }

}
