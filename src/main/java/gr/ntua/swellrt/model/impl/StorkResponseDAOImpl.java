/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.model.impl;

import gr.ntua.swellrt.model.dao.StorkResponseDAO;
import gr.ntua.swellrt.pojo.AttributeTemplate;
import gr.ntua.swellrt.pojo.StorkResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 *
 * @author nikos
 */

@Component
public class StorkResponseDAOImpl implements StorkResponseDAO {

    Logger LOG = LoggerFactory.getLogger(StorkResponseDAOImpl.class);
    
    @Override
    public void saveResponse(StorkResponse response) {
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        StringBuilder sb = new StringBuilder();
        sb.append(response.getToken())
          .append(";")
          .append(response.getReceivedAttributes().get("eIdentifier").getValue())
          .append(";")
          .append(timestamp);
        
        LOG.info("Response:" + sb.toString());

        try { 
            Resource resource = new ClassPathResource("/files/test.csv");
            File file = new File (String.valueOf(resource.getFile()));
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            fw.write(sb.toString() + "\n");
            fw.flush();
            fw.close();
         } catch(IOException e){
            LOG.error("ERROR WITH FILE",e);
         }
    }

    @Override
    public List<StorkResponse> getMatchingResponsesByToken(String token) {
        List<StorkResponse> matchingResponses = new ArrayList();
        
        try { 
            Resource resource = new ClassPathResource("/files/test.csv");
            File file = new File (String.valueOf(resource.getFile()));
            Reader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            try{
                String line = bufferedReader.readLine();
                while(line != null) {
                  LOG.info(line);
                  String[] lineSplit = line.split(";");
                  if(lineSplit.length > 2 && lineSplit[0].equals(token)){
                      StorkResponse resp = new StorkResponse();
                      resp.setToken(lineSplit[0]);
                      AttributeTemplate eid = new AttributeTemplate(lineSplit[1],0,1);
                      resp.getReceivedAttributes().put("eIdentifier",eid);
                      resp.setTimestamp(lineSplit[2]);
                      matchingResponses.add(resp);
                  }
                  line = bufferedReader.readLine();
                }
            }finally{
                if(bufferedReader != null){
                    bufferedReader.close();
                }
            }
        } catch(IOException e){
            LOG.error("ERROR WITH FILE",e);
         }
        return matchingResponses;
    }
    
}
