/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.model.dmo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 *
 * @author nikos
 */
@Entity
@Table(name = "stork_attributes") //camelcase gets translated to e_id, in naming strategy is not set!!!
public class StorkAttributesDMO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "complex")
    private int complex;
    @Column(name = "required")
    private int required;

    @Column(name = "eidas_name")
    private String eidasName;
    
    @Column(name = "requested_loa")
    private String requestedLoa;
    
    @Column(name = "requested_stork_qaa")
    private int requestedStorkQaa;
    

    public String getRequestedLoa(){
        return this.requestedLoa;
    }
    public void setRequestedLoa(String loa){
        this.requestedLoa=loa;
    }
    
    public int getRequestedStorkQaa(){
        return this.requestedStorkQaa;
    }
    
    public void setRequestedStorkQaa(int storkQaa){
        this.requestedStorkQaa = storkQaa;
    }
    
//    @OneToMany(cascade = CascadeType.ALL)
//    private Set<StorkAttributeValuesDMO> values;

    public String getEidasName() {
        return this.eidasName;
    }

    public void setEidasName(String eidasName) {
        this.eidasName = eidasName;
    }

    public int getComplex() {
        return this.complex;
    }

    public void setComplex(int complex) {
        this.complex = complex;
    }

    public int getRequired() {
        return this.required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

//    public void setValues(Set<StorkAttributeValuesDMO> values) {
//        this.values = values;
//    }
//
//    public Set<StorkAttributeValuesDMO> getValues() {
//        return this.values;
//    }

    
    
    
}
