/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt;

import gr.ntua.swellrt.utils.AppUtils;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestCheckUAegeanIDFormat {

    @Test
    public void testFormatOK() {
        assertEquals(AppUtils.checkUAgeanIDFormat("XXX/1234"), true);
        assertEquals(AppUtils.checkUAgeanIDFormat("abc/1234"), true);
        assertEquals(AppUtils.checkUAgeanIDFormat("aXX/121232134"), true);
        assertEquals(AppUtils.checkUAgeanIDFormat("ABX/121232134"), true);
    }

    @Test
    public void testFormatFail() {
        assertEquals(AppUtils.checkUAgeanIDFormat("1XX/1234"), false);
        assertEquals(AppUtils.checkUAgeanIDFormat("X2X/1234"), false);
        assertEquals(AppUtils.checkUAgeanIDFormat("XX3/1234"), false);
        assertEquals(AppUtils.checkUAgeanIDFormat("XXX/123a4"), false);

    }

}
