/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestSwellrtEvent {
    
    
    @Test
    public void testParseString() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString =  "{\"path\":\"root.needs.12.completionDate\",\"data\":{\"projId\":\"local.net/s+hqwFSt0sybA\",\"summaryText\":\"There are %n% notifications\",\"collapseKey\":\"applice\",\"context\":\"needs\",\"style\":\"inbox\",\"title\":\"Jewelry\",\"message\":\"✔ a task 9\",\"notId\":\"889303311\"},\"waveid\":\"local.net/s+hqwFSt0sybA\"} ";
        
        SwellrtEvent event = mapper.readValue(jsonInString, SwellrtEvent.class);
        assertEquals(event.getPath(),"root.needs.12.completionDate");
        
        jsonInString =  "{\"path\":\"root.needs.12.completionDate\",  \"data\":{\"recipients\": [\"test@test.gr\",\"test2@test.gr\"],\"projId\":\"local.net/s+hqwFSt0sybA\",\"summaryText\":\"There are %n% notifications\",\"collapseKey\":\"applice\",\"context\":\"needs\",\"style\":\"inbox\",\"title\":\"Jewelry\",\"message\":\"✔ a task 9\",\"notId\":\"889303311\"},\"waveid\":\"local.net/s+hqwFSt0sybA\"} ";
        event = mapper.readValue(jsonInString, SwellrtEvent.class);
    }
    
    
}
