/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp;

import teem.loginapp.utils.PasswordDigest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestPasswordDigest {
    
    
    @Test
    public void testPassDigestCreation(){
        //aec09095-20a0-4dbe-a04e-7df23205c0ae/test@test.gr
        String password = "aec09095-20a0-4dbe-a04e-7df23205c0ae/test@test.gr";
        //PasswordDigest pd = new PasswordDigest(password);
        
        //salt: 6/CoyqvQx7JD6mT7WlCM9Q==
        //digest: 7Qg2uLDZEfE49di7Oj9KxZ2nfViI823LKHllMfspsoj5Z3Q2yfOA7tik5c7VibNGl6bUdTNKGx3+qfkIlc9rfw==
    
        PasswordDigest pd = new PasswordDigest(password.toCharArray());
        new String(pd.getSalt(), Charset.forName("UTF-8"));
        new String (pd.getDigest(), Charset.forName("UTF-8"));
          pd = new PasswordDigest(password.toCharArray());
    }
    
    
    
}
