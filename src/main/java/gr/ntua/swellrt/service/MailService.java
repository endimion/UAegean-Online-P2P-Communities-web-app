/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.service;

import gr.ntua.swellrt.pojo.SwellrtEvent;


/**
 *
 * @author nikos
 */
public interface MailService {
    
     
    public String prepareAndSend(String recipient, String message, String userName);
    
    public String sendEventMail(String recipient, SwellrtEvent evt);
    
    public String sendEmailsForEvent(SwellrtEvent evt);
}
