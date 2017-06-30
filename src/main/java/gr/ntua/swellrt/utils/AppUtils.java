/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import gr.ntua.swellrt.pojo.StorkAttributeTemplate;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nikos
 */
public class AppUtils {

    private final static Logger log = LoggerFactory.getLogger(AppUtils.class);
    
    
    private final static String UAGEANIDPATTERN = "^[a-z]{3}/\\d+";
    

    public static Map<String, StorkAttributeTemplate> parseStorkJSONResponse(String jsonString) throws IOException {
        ObjectMapper jmap = new ObjectMapper();
        TypeFactory typeFactory = jmap.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, StorkAttributeTemplate.class);
        return jmap.readValue(jsonString, mapType);
    }

    public static String getEmailFromToken(String token) {
        if (token.contains("/")) {
            try {
                return token.split("/")[1];
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static boolean checkIfValidTimestamp(String timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Date parsedDate = dateFormat.parse(timestamp);
            Date beforeThirtyMinutes = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
            return parsedDate.after(beforeThirtyMinutes);
        } catch (ParseException e) {
            return false;
        }
    }
    
    
    
    public static boolean checkUAgeanIDFormat(String uAgeanId){
    
    
        return uAgeanId.toLowerCase().matches(UAGEANIDPATTERN);
    }
    

}
