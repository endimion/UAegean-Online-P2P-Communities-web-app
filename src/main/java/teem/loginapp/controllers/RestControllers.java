/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import teem.loginapp.model.dmo.AccountBuilder.SwellrtAccountMngDMO;
import teem.loginapp.model.dmo.StrokAttributesMongoDMO;
import teem.loginapp.pojo.ReceivedStorkAttribute;
import teem.loginapp.pojo.AttributeList;
import teem.loginapp.pojo.AttributeTemplate;
import teem.loginapp.pojo.StorkResponse;
import teem.loginapp.pojo.ResponseForStork;
import teem.loginapp.pojo.SwellrtEvent;
import teem.loginapp.pojo.UserCredentials;
import teem.loginapp.service.MailService;
import teem.loginapp.service.StorkAttributeService;
import teem.loginapp.service.SwellrtAccountService;
import teem.loginapp.service.TeemProjectService;
import teem.loginapp.utils.AppUtils;
import teem.loginapp.utils.Wrappers;
import java.util.Arrays;
import java.util.HashMap;
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
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import teem.loginapp.pojo.ADResponse;
import teem.loginapp.pojo.IssErrorResponse;
import teem.loginapp.service.ActiveDirectoryService;
import teem.loginapp.service.PropertiesService;
import teem.loginapp.utils.IssErrorMapper;
import teem.loginapp.utils.Translator;

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

    @Autowired
    private TeemProjectService projectServ;

    @Autowired
    private PropertiesService props;

    @Autowired
    private ActiveDirectoryService adServ;

    private final String token = "95901e5b61fd7c4f5f952927347f0994d0e22a3166bf7c90fb0287e8b87058fa";

    @Value("${eidas.error.connection}")
    private String EIDAS_CON_ERROR;

    @Value("${eidas.error.consent}")
    private String EIDAS_CONSENT_ERROR;

    @Value("${eidas.error.qaa}")
    private String EIDAS_QAA_ERROR;

    @Value("${eidas.error.missing}")
    private String EIDAS_MISSING_ATTRIBUTE_ERROR;

    @RequestMapping("/attributeList")
    public @ResponseBody
    AttributeList getAttributeList() {
        if (Boolean.parseBoolean(props.getProperties().get("useEIDAS").toString())) {
            return Wrappers.wrapStorkAttrMongoDMOtoEidasAttrTmpl(attributeService.getEnabledMng());
        } else {
            return Wrappers.wrapStorkAttrMongoDMOtoStorkAttrTmpl(attributeService.getEnabledMng());
        }
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

            if (responseString.trim().equals("{}") || StringUtils.isEmpty(responseString.trim())
                    || (responseString.contains("StatusCode") && responseString.contains("StatusMessage"))) {
//                LOG.info("Error Response");
                if (responseString.contains("StatusCode") && responseString.contains("StatusMessage")) {
                    IssErrorResponse err = IssErrorMapper.wrapErrorToObject(responseString);
//                    LOG.info("Error Message: " + err.getStatusMessage().getValue());
//                    LOG.info("Error Code: " + err.getStatusCode().getValue());
                    LOG.info(err.getStatusCode().getValue());
                    if (err.getStatusCode().getValue().contains("202002") || err.getStatusCode().getValue().contains("202003")
                            || err.getStatusCode().getValue().contains("202005") || err.getStatusCode().getValue().contains("202011")
                            || err.getStatusCode().getValue().contains("202013") || err.getStatusCode().getValue().contains("202015")
                            || err.getStatusCode().getValue().contains("202016") || err.getStatusCode().getValue().contains("202017")) {

                        cacheManager.getCache("errors").put(token, EIDAS_CON_ERROR);
                    }
                    if (err.getStatusCode().getValue().contains("202007") || err.getStatusCode().getValue().contains("202012")) {
                        cacheManager.getCache("errors").put(token, EIDAS_CONSENT_ERROR);
                    }
                    if (err.getStatusCode().getValue().contains("202004")) {
                        cacheManager.getCache("errors").put(token, EIDAS_QAA_ERROR);
                    }
                    if (err.getStatusCode().getValue().contains("202010")) {
                        cacheManager.getCache("errors").put(token, EIDAS_MISSING_ATTRIBUTE_ERROR);
                    }

                } else {
                    LOG.info("empry response!!!");
                }
                return new ResponseForStork(false);
            }

            response.setToken(token);
            String email = AppUtils.getEmailFromToken(token);
            SwellrtAccountMngDMO account = Wrappers.wrapStorkResponseToSwellrtAccount(response, attributeService);
            //Add user to MSTeams
            String givenName = Translator.translateGreekWordToEnglishAlphaBet(account.getAttributes().get("CurrentGivenName").getValue());
            String surname = Translator.translateGreekWordToEnglishAlphaBet(account.getAttributes().get("CurrentFamilyName").getValue());
            String displayName = givenName + surname;
            String mailNick = displayName;
            String principalName = account.getAttributes().get("PersonIdentifier").getValue().replace("/", "").replace("-", "");
            String password = AppUtils.generateRandomADPass(8);
            if (!StringUtils.isEmpty(email)) {
                account.getHuman().setEmail(email);
                StringBuilder userNameBuilder = new StringBuilder();

                String firstName = account.getAttributes().get("FirstName") != null
                        ? account.getAttributes().get("FirstName").getValue()
                        : account.getAttributes().get("CurrentGivenName").getValue();

                String lastName = account.getAttributes().get("FamilyName") != null
                        ? account.getAttributes().get("FamilyName").getValue()
                        : account.getAttributes().get("CurrentFamilyName").getValue();

                userNameBuilder.append(firstName)
                        .append(" ")
                        .append(lastName);

                mailserv.prepareAndSend(email, REGISTER_SUBJECT, userNameBuilder.toString(), displayName, password);
            } else {
                //if no email is found then the user must allready be registed!
                if (accountService.findByEid(account.getEid()) == null) {
                    return new ResponseForStork(true);
                }
            }

            if (account.getLocalPassword() == null) {
                account.setLocalPassword("");
            }
            //Add user to MSTeams
            ADResponse resp = adServ.createUser(displayName, mailNick, givenName, surname, principalName, password);

            if (resp.getId() != null) {
                account.setMsTeamId(resp.getId());
                account.setMsTeamsName(principalName);
                account.setMsTeamsPass(password);
                accountService.saveOrUpdate(account);
                return new ResponseForStork(true);
            } else {
                LOG.info("Error from AD service. Id:  " + resp.getId() + " Status: " + resp.getStatus());
                return new ResponseForStork(false);
            }

        } catch (IOException e) {
            LOG.error("Error parsing JSON", e);
            return new ResponseForStork(false);
        }
    }

    @RequestMapping(value = "/getCredentials", method = RequestMethod.GET)
    @ResponseBody
    public UserCredentials getUserDetailsFromToken(@RequestParam(value = "token", required = true) String token,
            HttpServletRequest request) {

        UserCredentials creds = new UserCredentials();
        String pattern = "http://eidas.europa.eu/attributes/naturalperson/(.*)";
        Pattern r = Pattern.compile(pattern);
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
                UserCredentials res = Wrappers.wrapSwellrtAccounttoUserCredentials(account, enabledAttributesList);

                HashMap cleanedAttributes = new HashMap<String, ReceivedStorkAttribute>();
                res.getAttributes().entrySet().stream()
                        .forEach(e -> {
                            if (r.matcher(e.getKey()).find()
                                    && e.getValue() != null
                                    && (e.getValue().getValue() == null || e.getValue().getValue().equals(""))) {
                                String cleanedKey = e.getKey().replaceAll("http://eidas.europa.eu/attributes/naturalperson/", "");
                                if (res.getAttributes().get(cleanedKey) == null) {
                                    cleanedAttributes.put(cleanedKey, e.getValue());
                                }
                            } else {
                                cleanedAttributes.put(e.getKey(), e.getValue());
                            }
                        });
                res.setAttributes(cleanedAttributes);
                return res;

            } else {
                creds.setStatus("TOKEN_EXPIRED");
                return creds;
            }
        }

        creds.setStatus("NO_USER_FOUND_FOR_TOKEN");
        return creds;
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
    public @ResponseBody
    Callable<String> receiveEvent(@RequestHeader(value = "token", required = false) String token,
            @RequestBody SwellrtEvent event) {
        //for better scalability we return here a callable so that 
        // the request thread will be freed imidiately (i.e. before the 
        // response is sent, because the sending of emails is a time consuming task
        // this makes the webapp require Servlet 3.0 container)
        Callable<String> response = () -> {
            LOG.info("Got event token " + token);
            LOG.info("Event " + event.getWaveid());
            if (token != null && token.equals(this.token)) {
                if (event != null && event.getWaveid() != null) {
                    List<String> participants = projectServ.findByWave_id(event.getWaveid())
                            .getParticipants().stream()
                            .map(id -> {
                                LOG.info("Searching by ID " + id);
                                return accountService.findById(id);
                            }).filter(account -> {
                        return account != null && account.getHuman() != null
                                && !StringUtils.isEmpty(account.getHuman().getEmail());
                    }).map(account -> {
                        LOG.info("Email  " + account.getHuman().getEmail());
                        return account.getHuman().getEmail();
                    }).collect(Collectors.toList());
                    return mailserv.sendEmailsForEvent(event, participants);
                }
                return "no participants found for project" + event.getWaveid();

            } else {
                return "Invalid token";
            }
        };
        return response;
    }

    @RequestMapping(value = "/getParticipants", method = {RequestMethod.GET})
    public @ResponseBody
    Callable<List<String>> getParticipants(@RequestParam(value = "token", required = false) String token,
            @RequestParam(value = "projectId", required = false) String projectId,
            HttpServletRequest request) {

        Callable<List<String>> response = () -> {
            ValueWrapper optionalValue = cacheManager.getCache("ips").get(request.getRemoteAddr());
            List<String> resp = new ArrayList();
            if (optionalValue == null) {
                resp.add("UN_AUTHORIZED_IP");
                return resp;
            }
            if (!token.contains(optionalValue.get().toString())) {
                resp.add("IP_TOKEN_MISSMATCH");
                return resp;
            }
            SwellrtAccountMngDMO account = accountService.findByToken(token);
            if (account == null || StringUtils.isEmpty(account.getId())) {
                resp.add("Account is not valid");
                return resp;
            }
            return projectServ.getParticipantsIfPromoter(projectId, account.getId());
        };

        return response;
    }

}
