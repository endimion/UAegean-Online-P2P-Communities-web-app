/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.model.dmo;

/**
 *
 * @author nikos
 */
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "storkPersons")
public class StorkPersonMngDMO {

    @Id
    private String id;

    private String eid;

    private String token;

    private String timestamp;

    private String username;

    private String password;

    private String email;

    private String localPassword;

    @DBRef
    private Set<StorkAttributeValueMongoDMO> attributesValues;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public String getLocalPassword() {
        return localPassword;
    }

    public void setLocalPassword(String localPassword) {
        this.localPassword = localPassword;
    }

    public Set<StorkAttributeValueMongoDMO> getAttributesValues() {
        return attributesValues;
    }

    public void setAttributesValues(Set<StorkAttributeValueMongoDMO> attributesValues) {
        this.attributesValues = attributesValues;
    }

    
    
    
}
