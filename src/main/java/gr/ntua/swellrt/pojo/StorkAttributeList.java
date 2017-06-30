/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gr.ntua.swellrt.utils.AppUtils;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nikos
 */
@JsonInclude(Include.NON_NULL)
public class StorkAttributeList {

//    @JsonInclude(Include.NON_NULL)
//    public class AttributeList {

//        private Map<String, StorkAttributeTemplate> attributes;
//
//        public AttributeList() {
//            this.attributes = new HashMap();
//        }
//
//        @JsonSerialize(using = SerializeMapToValues.class)
//        public Map<String, StorkAttributeTemplate> getAttributes() {
//            return attributes;
//        }
//    }

    private String status;
    private Map<String, StorkAttributeTemplate> list;

    public StorkAttributeList() {
        this.status = "OK";
        this.list = new HashMap();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    @JsonSerialize(using = SerializeMapToValues.class)
    public Map<String, StorkAttributeTemplate> getList() {
        return list;
    }

    public void setList(Map<String, StorkAttributeTemplate> list) {
        this.list = list;
    }


}
