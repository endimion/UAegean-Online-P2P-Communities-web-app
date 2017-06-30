/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.wrappersTest;

import static gr.ntua.swellrt.utils.Wrappers.getAqaaLevel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.JDOMException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestWrappers {

    @Test
    public void testGetAqaaLevel() {
        try {
            String test = "<t><course>STORK101</course>"
                    + "<programmeOfStudy>programmeOfStudy</programmeOfStudy><AQAA>2</AQAA>"
                    + "<nameOfInstitution>nameOfInstitution</nameOfInstitution></t>" ;
           
            assertEquals("2",getAqaaLevel(test));
            
            test = "<s><t><course>STORK101</course>"
                    + "<programmeOfStudy>programmeOfStudy</programmeOfStudy><AQAA>2</AQAA>"
                    + "<nameOfInstitution>nameOfInstitution</nameOfInstitution></t></s>" ;
            
            assertEquals("2",getAqaaLevel(test));
            
        } catch (JDOMException ex) {
            Logger.getLogger(TestWrappers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestWrappers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    

}
