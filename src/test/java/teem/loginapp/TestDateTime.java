/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp;

import teem.loginapp.utils.DateUtils;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestDateTime {
    
    @Test
    public void testTimeStampToddMMyyyy(){
     System.out.println(DateUtils.timeStampToddMMyyyy("1976-01-22T00%5800%5800.000Z"));
//        System.out.println(DateUtils.timeStampToddMMyyyy(null));
//        System.out.println(DateUtils.timeStampToddMMyyyy("1979-12-31T00X00X00.000Z"));
        System.out.println(DateUtils.timeStampToddMMyyyy("1983-10-04T00%5800%5800.000Z"));
    
    }
    
}
