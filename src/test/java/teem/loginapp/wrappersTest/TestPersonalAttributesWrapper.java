/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.wrappersTest;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import teem.loginapp.model.dmo.StrokAttributesMongoDMO;
import teem.loginapp.utils.Wrappers;

/**
 *
 * @author nikos
 */
public class TestPersonalAttributesWrapper {

    private List<StrokAttributesMongoDMO> attributes;

    @Before
    public void before() {
        attributes = new ArrayList();
        StrokAttributesMongoDMO att1 = new StrokAttributesMongoDMO();
        att1.setEidasName("http://eidas.europa.eu/attributes/legalperson/EORI");
        att1.setFriendlyName("EORI");

        StrokAttributesMongoDMO att2 = new StrokAttributesMongoDMO();
        att2.setEidasName("http://eidas.europa.eu/attributes/naturalperson/BirthName");
        att2.setFriendlyName("BirthName");

        attributes.add(att1);
        attributes.add(att2);
    }

    @Test
    public void testWrapper() {
        List<String> natural = Wrappers.getPersonalAttributes(attributes);
        List<String> legal = Wrappers.getLegalAttributes(attributes);

        assertEquals(natural.get(0), "BirthName");
        assertEquals(legal.get(0), "EORI");
    }

}
