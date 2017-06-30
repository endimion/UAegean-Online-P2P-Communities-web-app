/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.model.dmo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author nikos
 */
@Document(collection = "attributes")
public class StrokAttributesMongoDMO {

    @Id
    private String id;
    
    
    private String name;
    
    private boolean enabled;

    
    private int complex;
    
    private int required;

    
    private String eidasName;
    
    
    private String requestedLoa;
    
    
    private int requestedStorkQaa;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getComplex() {
        return complex;
    }

    public void setComplex(int complex) {
        this.complex = complex;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public String getEidasName() {
        return eidasName;
    }

    public void setEidasName(String eidasName) {
        this.eidasName = eidasName;
    }

    public String getRequestedLoa() {
        return requestedLoa;
    }

    public void setRequestedLoa(String requestedLoa) {
        this.requestedLoa = requestedLoa;
    }

    public int getRequestedStorkQaa() {
        return requestedStorkQaa;
    }

    public void setRequestedStorkQaa(int requestedStorkQaa) {
        this.requestedStorkQaa = requestedStorkQaa;
    }
    
    
    

}
