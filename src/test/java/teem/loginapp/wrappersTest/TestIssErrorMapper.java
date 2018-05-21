/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.wrappersTest;

import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import teem.loginapp.pojo.IssErrorResponse;
import teem.loginapp.utils.IssErrorMapper;

/**
 *
 * @author nikos
 */
public class TestIssErrorMapper {
    
    @Test
    public void testErrorFormatOk() throws IOException{
        String resp = "{\"StatusCode\":{\"value\":\"urn%58oasis%58names%58tc%58SAML%582.0%58status%58Requester\",\"complex\":\"0\",\"required\":\"0\"},\"StatusMessage\":{\"value\":\"202007 - Consent not given for a mandatory attribute.\",\"complex\":\"0\",\"required\":\"0\"}}";
        IssErrorResponse err =  IssErrorMapper.wrapErrorToObject(resp);
        assertEquals(err.getStatusCode().getValue(),"urn%58oasis%58names%58tc%58SAML%582.0%58status%58Requester");
        assertEquals(err.getStatusMessage().getValue(),"202007 - Consent not given for a mandatory attribute.");
    }

    @Test(expected = IOException.class)
    public void testErrorFormatError() throws IOException{
        String resp = "{\"StatusCode\": \"1\", \"Statusessage\": \"message\"}";
        IssErrorMapper.wrapErrorToObject(resp);        
    }

}
