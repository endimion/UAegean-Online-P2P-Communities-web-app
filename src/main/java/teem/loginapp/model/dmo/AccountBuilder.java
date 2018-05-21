/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.model.dmo;

import teem.loginapp.pojo.ReceivedStorkAttribute;
import teem.loginapp.utils.PasswordDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author nikos
 */
public class AccountBuilder {

    public static class Human {

        private PasswordDigest passwordDigest;
        private String email;
        private String locale;

        public PasswordDigest getPasswordDigest() {
            return passwordDigest;
        }

        public void setPasswordDigest(PasswordDigest passwordDigest) {
            this.passwordDigest = passwordDigest;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }
    }

    @Document(collection = "account")
    public static class SwellrtAccountMngDMO {

        @Id
        private String id;
        private Human human;
        private String token;
        private String localPassword;
        private String timestamp;
        private String eid;
        private String openPassword;
        private Map<String, ReceivedStorkAttribute> attributes;

        private String azureId;
        private String principalName;
        private String azurePassword;
        private String nativeCurrentGivenName;
        private String nativeFamilyGivenName;
        private String engCurrentGivenName;
        private String engCurrentFamilyName;
        private String aegeanId;

        public SwellrtAccountMngDMO() {
        }

        private SwellrtAccountMngDMO(AccountBuilder builder) {
            Human h = new Human();
            PasswordDigest pd = new PasswordDigest(builder.getPassword().toLowerCase()
                    .toCharArray());
            h.setPasswordDigest(pd);
            h.setEmail(builder.getEmail());
            h.setLocale("en_us");
            this.human = h;
            this.localPassword = builder.getLocalPassword();
            this.token = builder.getToken();
            this.attributes = new HashMap();
            this.timestamp = builder.getTimestamp();
            if (builder.getAttributes() != null) {
                builder.getAttributes().stream().forEach(attribute -> {
                    if (attribute.geteIDASName() != null) {
                        String attributeName = attribute.geteIDASName()
                                .replace("http://eidas.europa.eu/attributes/naturalperson/representative/", "")
                                .replace("http://eidas.europa.eu/attributes/legalperson/representative/", "")
                                .replace("http://eidas.europa.eu/attributes/naturalperson/", "")
                                .replace("http://eidas.europa.eu/attributes/legalperson/", "");
                        attributes.put(attributeName, attribute);
                    } else {
                        attributes.put(attribute.getStorkName(), attribute);
                    }
                });
            }

            this.id = builder.getUsername().toLowerCase() + "@local.net";
            this.eid = builder.getEid();
            this.openPassword = builder.getOpenPassword();
            this.azureId = builder.getMsTeamId();
            this.principalName = builder.getMsTeamsName();
            this.azurePassword = builder.getMsTeamsPass();

            this.nativeCurrentGivenName = builder.getNativeCurrentGivenName();
            this.nativeFamilyGivenName = builder.getNativeFamilyGivenName();
            this.engCurrentFamilyName = builder.getEngCurrentFamilyName();
            this.engCurrentGivenName = builder.getEngCurrentGivenName();
            this.aegeanId = builder.getAegeanId();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public Human getHuman() {
            return human;
        }

        public void setHuman(Human human) {
            this.human = human;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getLocalPassword() {
            return localPassword;
        }

        public void setLocalPassword(String localPassword) {
            this.localPassword = localPassword;
        }

        public Map<String, ReceivedStorkAttribute> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, ReceivedStorkAttribute> attributes) {
            this.attributes = attributes;
        }

        public String getEid() {
            return eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public String getOpenPassword() {
            return openPassword;
        }

        public void setOpenPassword(String openPassword) {
            this.openPassword = openPassword;
        }

        public String getMsTeamId() {
            return azureId;
        }

        public void setMsTeamId(String msTeamId) {
            this.azureId = msTeamId;
        }

        public String getMsTeamsName() {
            return principalName;
        }

        public void setMsTeamsName(String msTeamsName) {
            this.principalName = msTeamsName;
        }

        public String getMsTeamsPass() {
            return azurePassword;
        }

        public void setMsTeamsPass(String msTeamsPass) {
            this.azurePassword = msTeamsPass;
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

        public String getAegeanId() {
            return aegeanId;
        }

        public void setAegeanId(String aegeanId) {
            this.aegeanId = aegeanId;
        }

    }

    private String id;
//    private Human human;

    private String username;
    private String token;
    private String localPassword;
    private String timestamp;
    private List<ReceivedStorkAttribute> attributes;
    private String openPassword;
    private String password;
    private String email;
    private String locale;
    private String eid;

    //
    private String azureId;
    private String userPrincipal;
    private String azurePass;
    private String nativeCurrentGivenName;
    private String nativeFamilyGivenName;
    private String engCurrentGivenName;
    private String engCurrentFamilyName;
    private String aegeanId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLocalPassword() {
        return localPassword;
    }

    public void setLocalPassword(String localPassword) {
        this.localPassword = localPassword;
    }

    public List<ReceivedStorkAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ReceivedStorkAttribute> attributes) {
        this.attributes = attributes;
    }

    public SwellrtAccountMngDMO build() {
        return new SwellrtAccountMngDMO(this);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getOpenPassword() {
        return openPassword;
    }

    public void setOpenPassword(String openPassword) {
        this.openPassword = openPassword;
    }

    public String getMsTeamId() {
        return azureId;
    }

    public void setMsTeamId(String msTeamId) {
        this.azureId = msTeamId;
    }

    public String getMsTeamsName() {
        return userPrincipal;
    }

    public void setMsTeamsName(String msTeamsName) {
        this.userPrincipal = msTeamsName;
    }

    public String getMsTeamsPass() {
        return azurePass;
    }

    public void setMsTeamsPass(String msTeamsPass) {
        this.azurePass = msTeamsPass;
    }

    public String getAzureId() {
        return azureId;
    }

    public void setAzureId(String azureId) {
        this.azureId = azureId;
    }

    public String getUserPrincipal() {
        return userPrincipal;
    }

    public void setUserPrincipal(String userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    public String getAzurePass() {
        return azurePass;
    }

    public void setAzurePass(String azurePass) {
        this.azurePass = azurePass;
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

    public String getAegeanId() {
        return aegeanId;
    }

    public void setAegeanId(String aegeanId) {
        this.aegeanId = aegeanId;
    }

}
