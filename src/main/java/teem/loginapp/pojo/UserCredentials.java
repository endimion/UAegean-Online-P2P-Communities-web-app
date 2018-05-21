/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.pojo;

import java.util.Map;

/**
 *
 * @author nikos
 */
public class UserCredentials {

    private String username;
    private String password;
    private String email;
    private String status;
    private Map<String, ReceivedStorkAttribute> attributes;

    
    
    
    private String azureId;
    private String principalName;
    private String azurePassword;
    private String nativeCurrentGivenName;
    private String nativeFamilyGivenName;
    private String engCurrentGivenName;
    private String engCurrentFamilyName;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, ReceivedStorkAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, ReceivedStorkAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getAzureId() {
        return azureId;
    }

    public void setAzureId(String azureId) {
        this.azureId = azureId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getAzurePassword() {
        return azurePassword;
    }

    public void setAzurePassword(String azurePassword) {
        this.azurePassword = azurePassword;
    }

    public String getNativeCurrentGivenName() {
        return nativeCurrentGivenName;
    }

    public void setNativeCurrentGivenName(String nativeCurrentGivenName) {
        this.nativeCurrentGivenName = nativeCurrentGivenName;
    }

    public String getNativeFamilyGivenName() {
        return nativeFamilyGivenName;
    }

    public void setNativeFamilyGivenName(String nativeFamilyGivenName) {
        this.nativeFamilyGivenName = nativeFamilyGivenName;
    }

    public String getEngCurrentGivenName() {
        return engCurrentGivenName;
    }

    public void setEngCurrentGivenName(String engCurrentGivenName) {
        this.engCurrentGivenName = engCurrentGivenName;
    }

    public String getEngCurrentFamilyName() {
        return engCurrentFamilyName;
    }

    public void setEngCurrentFamilyName(String engCurrentFamilyName) {
        this.engCurrentFamilyName = engCurrentFamilyName;
    }

    
    
}
