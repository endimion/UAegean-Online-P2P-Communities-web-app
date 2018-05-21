/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import teem.loginapp.pojo.IssErrorResponse;

/**
 *
 * @author nikos
 */
public class IssErrorMapper {


    public static IssErrorResponse wrapErrorToObject(String errorJson) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(errorJson, IssErrorResponse.class);
    }

    
}
