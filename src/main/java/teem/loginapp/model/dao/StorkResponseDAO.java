/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.model.dao;

import teem.loginapp.pojo.StorkResponse;
import java.util.List;

/**
 *
 * @author nikos
 */
public interface StorkResponseDAO {
    
    public void saveResponse(StorkResponse response);
    
    public List<StorkResponse> getMatchingResponsesByToken(String token);
    
}
