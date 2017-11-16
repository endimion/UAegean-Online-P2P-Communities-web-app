/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.serviceTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import teem.loginapp.pojo.SwellrtEvent;
import teem.loginapp.serviceImpl.MailContentBuilder;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestMailContentBuilder {

    @Test
    public void test() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "{\"path\":\"root.needs.12.completionDate\",\"data\":{\"projId\":\"local.net/s+hqwFSt0sybA\",\"summaryText\":\"There are %n% notifications\",\"collapseKey\":\"applice\",\"context\":\"needs\",\"style\":\"inbox\",\"title\":\"Jewelry\",\"message\":\"✔ a task 9\",\"notId\":\"889303311\"},\"waveid\":\"local.net/s+hqwFSt0sybA\"} ";

        SwellrtEvent event = mapper.readValue(jsonInString, SwellrtEvent.class);
        MailContentBuilder builder = new MailContentBuilder();
        System.out.println(builder.buildEventContent(event));
    
    }
}
