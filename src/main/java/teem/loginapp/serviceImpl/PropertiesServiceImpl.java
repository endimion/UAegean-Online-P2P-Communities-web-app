/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.serviceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import teem.loginapp.service.PropertiesService;

/**
 *
 * @author nikos
 */
@Service
public class PropertiesServiceImpl implements PropertiesService {

    Logger log = LoggerFactory.getLogger(PropertiesService.class);

    private Properties property = new Properties();

    public PropertiesServiceImpl() {
        InputStream input;
        String dir;
        try {
            dir = System.getProperty("user.home");
            log.info(dir + "/loginapp/config.properties");
            input = new FileInputStream(dir + "/loginapp/config.properties");
            this.property.load(input);
            input.close();
        } catch (FileNotFoundException ex) {
            log.info("File NOT FOUND");
            log.error(ex.getStackTrace().toString());
        } catch (IOException ex) {
            log.info("IOException error");
            log.error(ex.getStackTrace().toString());
        }
    }

    @Override
    public Properties getProperties() {

        return this.property;
    }

    @Override
    public String getNode() {
        if (Boolean.parseBoolean(this.property.getProperty("useEIDAS"))) {
            return this.property.getProperty("eidasISS.url");
        } else {
            return this.property.getProperty("storkISS.url");
        }
    }

    @Override
    public String getSP() {
        if (Boolean.parseBoolean(this.property.getProperty("useEIDAS"))) {
            return this.property.getProperty("eidasISS.sp");
        } else {
            return this.property.getProperty("storkISS.sp");
        }
    }

    @Override
    public String getSamlType() {
        if (Boolean.parseBoolean(this.property.getProperty("useEIDAS"))) {
            return "eIDAS";
        } else {
            return "STORK";
        }
    }

    @Override
    public String getServer() {
        return this.property.getProperty("server");
    }

}
