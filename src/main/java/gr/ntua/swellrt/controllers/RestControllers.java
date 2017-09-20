/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.controllers;

import gr.ntua.swellrt.model.dmo.AccountBuilder.SwellrtAccountMngDMO;
import gr.ntua.swellrt.model.dmo.StrokAttributesMongoDMO;
import gr.ntua.swellrt.pojo.ReceivedStorkAttribute;
import gr.ntua.swellrt.pojo.AttributeList;
import gr.ntua.swellrt.pojo.AttributeTemplate;
import gr.ntua.swellrt.pojo.StorkResponse;
import gr.ntua.swellrt.pojo.ResponseForStork;
import gr.ntua.swellrt.pojo.SwellrtEvent;
import gr.ntua.swellrt.pojo.UserCredentials;
import gr.ntua.swellrt.service.MailService;
import gr.ntua.swellrt.service.StorkAttributeService;
import gr.ntua.swellrt.service.SwellrtAccountService;
import gr.ntua.swellrt.utils.AppUtils;
import gr.ntua.swellrt.utils.Wrappers;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author nikos
 */
@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
public class RestControllers {

    Logger LOG = LoggerFactory.getLogger(RestControllers.class);

    @Autowired
    private MailService mailserv;

    private final static String REGISTER_SUBJECT = "Registration";

    @Autowired
    private SwellrtAccountService accountService;

    @Autowired
    private StorkAttributeService attributeService;

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("/attributeList")
    public @ResponseBody
    AttributeList getAttributeList() {
//        return Wrappers.wrapStorkAttrMongoDMOtoStorkAttrTmpl(attributeService.getEnabledMng());
        return Wrappers.wrapStorkAttrMongoDMOtoEidasAttrTmpl(attributeService.getEnabledMng());
    }

    

    @RequestMapping("/getToken")
    public @ResponseBody
    String getToken() {
        return UUID.randomUUID().toString();
    }

    @RequestMapping(value = "/saveJSONPOST", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseForStork saveStorkResponseJSONPOST(
            @RequestParam(value = "r", required = false) String responseString,
            @RequestParam(value = "t", required = false) String token) {
        try {
            StorkResponse response = new StorkResponse();
            Map<String, AttributeTemplate> receivedValues = AppUtils.parseStorkJSONResponse(responseString);
            response.setReceivedAttributes(receivedValues);
            LOG.info("received the string: \n" + responseString);
            response.setToken(token);
            String email = AppUtils.getEmailFromToken(token);

            SwellrtAccountMngDMO account = Wrappers.wrapStorkResponseToSwellrtAccount(response, attributeService);
            if (!StringUtils.isEmpty(email)) {
                account.getHuman().setEmail(email);
                StringBuilder userNameBuilder = new StringBuilder();
                
                String firstName =account.getAttributes().get("FirstName")!= null?
                            account.getAttributes().get("FirstName").getValue():
                            account.getAttributes().get("CurrentGivenName").getValue();
                
                String lastName = account.getAttributes().get("FamilyName")!= null?
                            account.getAttributes().get("FamilyName").getValue():
                            account.getAttributes().get("CurrentFamilyName").getValue();
                
                userNameBuilder.append(firstName)
                        .append(" ")
                        .append(lastName);

                mailserv.prepareAndSend(email, REGISTER_SUBJECT, userNameBuilder.toString());
            }

            if (account.getLocalPassword() == null) {
                account.setLocalPassword("");
            }
            accountService.saveOrUpdate(account);

            return new ResponseForStork(true);
        } catch (Exception e) {
            LOG.error("Error parsing JSON", e);
            return new ResponseForStork(false);
        }
    }

    @RequestMapping(value = "/getCredentials", method = RequestMethod.GET)
    @ResponseBody
    public UserCredentials getUserDetailsFromToken(@RequestParam(value = "token", required = true) String token,
            HttpServletRequest request) {
//        PersonSqlDMO res = eidService.findByToken(token);
//        StorkPersonMngDMO person = personService.findByToken(token);
        UserCredentials creds = new UserCredentials();

        SwellrtAccountMngDMO account = accountService.findByToken(token);

        ValueWrapper optionalValue = cacheManager.getCache("ips").get(request.getRemoteAddr());
        if (optionalValue == null) {
            creds.setStatus("UN_AUTHORIZED_IP");
            return creds;
        }
        LOG.info("IP " + optionalValue.get().toString());
        LOG.info("TOKEN " + token);

        if (!token.contains(optionalValue.get().toString())) {
            creds.setStatus("IP_TOKEN_MISSMATCH");
            return creds;
        }

        if (account != null) {
            if (AppUtils.checkIfValidTimestamp(account.getTimestamp())) {
                List<StrokAttributesMongoDMO> enabledAttributesList = attributeService.getEnabledMng();
                return Wrappers.wrapSwellrtAccounttoUserCredentials(account, enabledAttributesList);
            } else {
                creds.setStatus("TOKEN_EXPIRED");
                return creds;
            }
        }

        creds.setStatus("NO_USER_FOUND_FOR_TOKEN");
        return creds;
    }

    @RequestMapping(value = "/saveUser", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseForStork saveStorkResponseToMONGO(
            @RequestParam(value = "r", required = false) String responseString,
            @RequestParam(value = "t", required = false) String token) {
        try {
            StorkResponse response = new StorkResponse();
            Map<String, AttributeTemplate> receivedValues = AppUtils.parseStorkJSONResponse(responseString);
            response.setReceivedAttributes(receivedValues);
            LOG.info("received the string: \n" + responseString);
            response.setToken(token);
            String email = AppUtils.getEmailFromToken(token);
            SwellrtAccountMngDMO account = Wrappers.wrapStorkResponseToSwellrtAccount(response, attributeService);
            account.getHuman().setEmail(email);
            if (account.getLocalPassword() == null) {
                account.setLocalPassword("");
            }
            accountService.saveOrUpdate(account);
            StringBuilder userNameBuilder = new StringBuilder();
            userNameBuilder.append(account.getAttributes().get("FirstName").getValue())
                    .append(" ")
                    .append(account.getAttributes().get("FamilyName").getValue());
            mailserv.prepareAndSend(email, REGISTER_SUBJECT, userNameBuilder.toString());
            return new ResponseForStork(true);
        } catch (Exception e) {
            LOG.error("Error parsing JSON", e);
            return new ResponseForStork(false);
        }
    }

    @CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    @RequestMapping(value = {"/updateAttribute/{attr}/{value:.+}/",
        "/updateAttribute/{attr}/{value:.+}/{valueTail:.+}/"}, method = {RequestMethod.PUT, RequestMethod.GET})
    public @ResponseBody
    ResponseForStork updateAttribute(HttpSession httpSession, @PathVariable String attr,
            @PathVariable String value, @PathVariable Optional<String> valueTail, HttpServletRequest request) {

        if (valueTail.isPresent()) {
            value += "/" + valueTail.get();
        }
        Cookie[] cookies = request.getCookies();
        Optional<String> eidCookie = (cookies != null) ? Arrays.stream(cookies).filter(cookie -> {
            return cookie.getName().equals("eId");
        }).map(cookie -> {
            return cookie.getValue();
        }).findFirst() : Optional.empty();

        LOG.info("EID in session " + eidCookie.isPresent());
        if (eidCookie.isPresent()) {
            String eId = eidCookie.get();
            SwellrtAccountMngDMO account = accountService.findByEid(eId);
            if (account.getAttributes().get(attr) != null) {
                account.getAttributes().get(attr).setValue(value);
            } else {
                if (!attr.equals("email")) {

                    if (attr.equals("UAgeanID") && !AppUtils.checkUAgeanIDFormat(value)) {
                        return new ResponseForStork(false);
                    }

                    ReceivedStorkAttribute newAttribute = new ReceivedStorkAttribute();
                    newAttribute.setStorkName(attr);
                    newAttribute.seteIDASName(attr);
                    newAttribute.setValue(value);
                    newAttribute.setLoa("low");
                    newAttribute.setRequestedStorkQAA("1");
                    newAttribute.setRequestedLoA("low");
                    newAttribute.setaQAA("1");
                    account.getAttributes().put(attr, newAttribute);
                }

            }
            if (attr.equals("email")) {
                account.getHuman().setEmail(value);
            }

            accountService.saveOrUpdateTeemAttributes(account);
            return new ResponseForStork(true);
        }
        return new ResponseForStork(false);
    }
    
    
     @RequestMapping(value = "/event", method = {RequestMethod.POST})
    public @ResponseBody Callable<String> receiveEvent(@RequestBody SwellrtEvent event){
        //for better scalability we return here a callable so that 
        // the request thread will be freed imidiately (i.e. before the 
        // response is sent, because the sending of emails is a time consuming task
        // this makes the webapp require Servlet 3.0 container)
        Callable<String> response = () -> mailserv.sendEmailsForEvent(event);
        return response;
    }
    
    
    
    

}
