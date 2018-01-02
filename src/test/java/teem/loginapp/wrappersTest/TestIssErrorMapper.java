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
        String resp = "{\"StatusCode\": \"1\", \"StatusMessage\": \"message\"}";
        IssErrorResponse err =  IssErrorMapper.wrapErrorToObject(resp);
        assertEquals(err.getStatusCode(),"1");
        assertEquals(err.getStatusMessage(),"message");
    }

    @Test(expected = IOException.class)
    public void testErrorFormatError() throws IOException{
        String resp = "{\"StatusCode\": \"1\", \"Statusessage\": \"message\"}";
        IssErrorMapper.wrapErrorToObject(resp);        
    }

}
