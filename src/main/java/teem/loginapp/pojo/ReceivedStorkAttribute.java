/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.pojo;

/**
 *
 * @author nikos
 */
public class ReceivedStorkAttribute {

    private String value;
    private String requestedLoA;
    private String requestedStorkQAA;
    private String aQAA;
    private String loa;
    private String storkName;
    private String eIDASName;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRequestedLoA() {
        return requestedLoA;
    }

    public void setRequestedLoA(String requestedLoA) {
        this.requestedLoA = requestedLoA;
    }

    public String getRequestedStorkQAA() {
        return requestedStorkQAA;
    }

    public void setRequestedStorkQAA(String requestedStorkQAA) {
        this.requestedStorkQAA = requestedStorkQAA;
    }

    public String getaQAA() {
        return aQAA;
    }

    public void setaQAA(String aQAA) {
        this.aQAA = aQAA;
    }

    public String getLoa() {
        return loa;
    }

    public void setLoa(String loa) {
        this.loa = loa;
    }

    public String getStorkName() {
        return storkName;
    }

    public void setStorkName(String storkName) {
        this.storkName = storkName;
    }

    public String geteIDASName() {
        return eIDASName;
    }

    public void seteIDASName(String eIDASName) {
        this.eIDASName = eIDASName;
    }

}
