/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.service;


/**
 *
 * @author nikos
 */
public interface MailService {
    
     
    public void prepareAndSend(String recipient, String message, String userName);
}
