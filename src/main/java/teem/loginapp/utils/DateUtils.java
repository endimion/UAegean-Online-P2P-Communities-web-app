/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nikos
 */
public class DateUtils {

    private static final String[] formats = {
        "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssZ",
        "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss",
        "MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'",
        "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS",
        "MM/dd/yyyy'T'HH:mm:ssZ", "MM/dd/yyyy'T'HH:mm:ss",
        "yyyy:MM:dd HH:mm:ss", "yyyyMMdd"};

    public static String timeStampToddMMyyyy(String timestamp) {
        //1979-12-31T00X00X00.000Z
        //"yyyy-MM-dd'T'HHXmmXssXSSSZ"

        Date res = null;
        SimpleDateFormat sdf = null;
        List<String> matchingFormats = new ArrayList();
        if (timestamp != null) {
            timestamp = timestamp.replaceAll("X", ":").replaceAll("%", ":");

            for (String parse : formats) {
                sdf = new SimpleDateFormat(parse);
                try {
                    res = sdf.parse(timestamp);
                    System.out.println("Printing the value of " + parse);
                    matchingFormats.add(parse);
                } catch (ParseException e) {

                }
            }
           
            String frmt = matchingFormats.stream().sorted( (f1,f2) -> {
                return f2.length()- f1.length();
            }).findFirst().orElse("yyyyMMdd"); //
            sdf = new SimpleDateFormat(frmt);
          try{
              res = sdf.parse(timestamp);
          }catch(ParseException e){
          
          }
          sdf = new SimpleDateFormat("dd-MM-yyyy");
        }
        
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(res);
        cal.add(Calendar.DATE, -3);
        res = cal.getTime();
        
        return (res != null && sdf != null) ? sdf.format(res) : "";
    }
    
    

    

}
