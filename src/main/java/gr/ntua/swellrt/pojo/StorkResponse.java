/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.pojo;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author nikos
 */
public class StorkResponse {

//    private StorkAttributeTemplate eIdentifier;
    private Map<String, StorkAttributeTemplate> receivedAttributes;
    private String token;
    private String status;
    private String timestamp;

    /*
    {
        "attribute name ":{
        "value":attribute value or null,"complex":0 or 1 depending on the value above,
        "required":0 if optional or 1 if mandatory
        },
        ...
    }
    {"givenName":{"value":"???????","complex":"0","required":"0"},
    "eIdentifier":{"value":"GR/GR/ERMIS-11076669","complex":"0","required":"0"}}
     */
    public StorkResponse() {
        this.receivedAttributes = new HashMap<>();
    }

    public Map<String, StorkAttributeTemplate> getReceivedAttributes() {
        return receivedAttributes;
    }

    public void setReceivedAttributes(Map<String, StorkAttributeTemplate> receivedAttributes) {
        this.receivedAttributes = receivedAttributes;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.receivedAttributes.get("eIdentifier"))
                .append("||")
                .append(this.status)
                .append("||")
                .append(this.timestamp)
                .append("||")
                .append(this.token)
                .append("||")
                .append(this.receivedAttributes.get("givenName"));
        return sb.toString();
    }

}
