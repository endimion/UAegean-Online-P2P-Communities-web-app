/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.wrappersTest;

import java.io.IOException;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import teem.loginapp.pojo.AttributeTemplate;
import teem.loginapp.utils.AppUtils;

/**
 *
 * @author nikos
 */
public class testWrapResponse {
    
    
    @Test
    public void testParseStorkJSONResponseNoException(){
        String resp = "{\"http://eidas.europa.eu/attributes/naturalperson/CurrentAddress\":{\"value\":\"null\",\"complex\":\"0\",\"required\":\"0\"},\"http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName\":{\"value\":\"javier\",\"complex\":\"0\",\"required\":\"1\"},\"http://eidas.europa.eu/attributes/naturalperson/PlaceOfBirth\":{\"value\":\"Place of Birth\",\"complex\":\"0\",\"required\":\"0\"},\"http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName\":{\"value\":\"Garcia\",\"complex\":\"0\",\"required\":\"1\"},\"http://eidas.europa.eu/attributes/naturalperson/DateOfBirth\":{\"value\":\"1980-01-01\",\"complex\":\"0\",\"required\":\"1\"},\"http://eidas.europa.eu/attributes/naturalperson/Gender\":{\"value\":\"Male\",\"complex\":\"0\",\"required\":\"0\"},\"http://eidas.europa.eu/attributes/naturalperson/PersonIdentifier\":{\"value\":\"CD/GR/12345\",\"complex\":\"0\",\"required\":\"1\"}}";
        try {   
            Map<String, AttributeTemplate> receivedValues = AppUtils.parseStorkJSONResponse(resp);
            resp = "ok";
        } catch (IOException ex) {
            Assert.fail("Expected no exception to be thrown");
        }
    
    }
    
    
    @Test
    public void testParseStorkJSONResponseException(){
        String resp = "{\"http://eidas.europa.eu/attributes/naturalperson/CurrentAddress\":{\"value\":\"null\",\"complex\":\"0\",\"required\":\"0\"},\"http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName\":{\"value\":\"javier\",\"complex\":\"0\",\"required\":\"1\"},\"http://eidas.europa.eu/attributes/naturalperson/PlaceOfBirth\":{\"value\":\"Place of Birth\",\"complex\":\"0\",\"required\":\"0\"},\"http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName\":{\"value\":\"Garcia\",\"complex\":\"0\",\"required\":\"1\"},\"http://eidas.europa.eu/attributes/naturalperson/DateOfBirth\":{\"value\":\"1980-01-01\",\"complex\":\"0\",\"required\":\"1\"},\"http://eidas.europa.eu/attributes/naturalperson/Gender\":{\"value\":\"Male\",\"complex\":\"0\",\"required\":\"0\"},\"http://eidas.europa.eu/attributes/naturalperson/PersonIdentifier\":{\"value\":\"CD/GR/12345\",\"complex\":\"0\",\"required\":\"1\"}}!!!";
        try {   
            Map<String, AttributeTemplate> receivedValues = AppUtils.parseStorkJSONResponse(resp);
            Assert.fail("Expected exception to be thrown");   
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
    }
    
}
