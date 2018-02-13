/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestRegex {

    @Test
    public void testRegex() {
        String pattern = "http://eidas.europa.eu/attributes/naturalperson/(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher("http://eidas.europa.eu/attributes/naturalperson/PlaceOfBirth");
        assertEquals(m.find(), true);
        
        m = r.matcher("http://eidas.europa");
        assertEquals(m.find(),false);
    }

}
