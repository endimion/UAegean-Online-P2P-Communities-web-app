/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.serviceTests;

import org.junit.Test;
import teem.loginapp.service.PropertiesService;
import teem.loginapp.serviceImpl.PropertiesServiceImpl;

/**
 *
 * @author nikos
 */
public class PropertiesServiceTest {
    
    @Test
    public void testService(){
    
        PropertiesService props = new PropertiesServiceImpl();
        System.out.println("TEST " + props.getNode());
    }
    
    
}
