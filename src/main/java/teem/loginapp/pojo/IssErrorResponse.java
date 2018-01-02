/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.pojo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 *
 * @author nikos
 */
public class IssErrorResponse {
    
    
    private String statusCode;
    private String statusMessage;

    public IssErrorResponse(){
    
    }
//    
//    public IssErrorResponse(String statusCode, String statusMessage){
//        this.statusCode = statusCode;
//        this.statusMessage = statusMessage;
//    }
    
    @JsonGetter("StatusCode")
    public String getStatusCode() {
        return statusCode;
    }

    @JsonSetter("StatusCode")
    public void setStatusCode(String StatusCode) {
        this.statusCode = StatusCode;
    }

    @JsonGetter("StatusMessage")
    public String getStatusMessage() {
        return statusMessage;
    }

    @JsonSetter("StatusMessage")
    public void setStatusMessage(String StatusMessage) {
        this.statusMessage = StatusMessage;
    }
    
    
    
}
