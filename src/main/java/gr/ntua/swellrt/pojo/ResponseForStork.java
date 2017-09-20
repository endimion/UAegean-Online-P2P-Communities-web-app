/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.pojo;

/**
 *
 * @author nikos
 */
public class ResponseForStork {
    
    private String status;
    private final static String OK_STATUS = "OK";
    private final static String FAIL_STATUS = "FAIL";
    
    public ResponseForStork(boolean status){
        this.status=(status)?OK_STATUS:FAIL_STATUS;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}