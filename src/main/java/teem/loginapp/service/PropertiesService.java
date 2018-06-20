/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.service;

import java.util.Properties;

/**
 *
 * @author nikos
 */
public interface PropertiesService {
    public Properties getProperties();
    
    public String getNode();
     public String getPreNode();
    public String getSP();
    public String getSamlType();
    public String getServer();
}
