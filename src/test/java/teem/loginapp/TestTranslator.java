/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp;

import teem.loginapp.utils.Translator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestTranslator {

    @Test
    public void testTranslate() {
        String name = "Νικοσ";
        String res = Translator.translateGreekWordToEnglishAlphaBet(name);
        
        assertEquals("NIKOS",res);
        name = "test";
        assertEquals(name.toUpperCase(),Translator.translateGreekWordToEnglishAlphaBet(name));
    }
}
