/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.serviceImpl;

import teem.loginapp.model.dao.StorkResponseDAO;
import teem.loginapp.pojo.StorkResponse;
import teem.loginapp.service.StorkResponseService;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nikos
 */
@Component
public class StorkResponseServiceImpl implements StorkResponseService {

   @Autowired
   private StorkResponseDAO responseDAO; 
   
   Logger LOG = LoggerFactory.getLogger(StorkResponseServiceImpl.class);
    
    @Override
    public String getEidIfValidToken(String token) {
        List<StorkResponse> responses = responseDAO.getMatchingResponsesByToken(token);
        
        Optional<StorkResponse> result = responses.stream().filter(resp -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            try{
                Date parsedDate = dateFormat.parse(resp.getTimestamp());
                Date beforeThirtyMinutes = new Date(System.currentTimeMillis()-30*60*1000);
                return parsedDate.after(beforeThirtyMinutes);
            }catch(ParseException e){
                LOG.error("Error Parsing date " + resp.getTimestamp(), e);
            }
            return false;
        }).findFirst();
        
        if(result.isPresent()){
            return result.get().getReceivedAttributes().get("eIdentifier").getValue();
        }
        
        return null;
    }
    
}
