/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.model.dmo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author nikos
 */
@Document(collection = "attributeValues")
public class StorkAttributeValueMongoDMO {

    @Id
    private String id;

    private String value;

    private int aqaa;

    private String loa;

    @DBRef
//    @CascadeSave
    private StrokAttributesMongoDMO attribute;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getAqaa() {
        return aqaa;
    }

    public void setAqaa(int aqaa) {
        this.aqaa = aqaa;
    }

    public String getLoa() {
        return loa;
    }

    public void setLoa(String loa) {
        this.loa = loa;
    }

    public StrokAttributesMongoDMO getAttribute() {
        return attribute;
    }

    public void setAttribute(StrokAttributesMongoDMO attribute) {
        this.attribute = attribute;
    }
    
    

}
