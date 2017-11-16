/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.model.dmo;

import java.math.BigInteger;
import java.util.Set;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
 

/**
 *
 * @author nikos
 */
@Entity
@Table(name = "attribute_values") //camelcase gets translated to e_id, in naming strategy is not set!!!
public class StorkAttributeValuesDMO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name="aqaa")
    private int aqaa;
    
    @Column(name="loa")
    private String loa;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attr_id")
    private StorkAttributesDMO attribute;

//    @ManyToMany(cascade = javax.persistence.CascadeType.ALL,
//            fetch = FetchType.LAZY, mappedBy = "attributesValues")
//    private Set<EidDMO> eids;
//
//    public Set<EidDMO> getEids() {
//        return eids;
//    }
//
//    public void setEids(Set<EidDMO> eids) {
//        this.eids = eids;
//    }

   

    public StorkAttributesDMO getAttribute() {
        return attribute;
    }

    public void setAttribute(StorkAttributesDMO attribute) {
        this.attribute = attribute;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    
    

}
