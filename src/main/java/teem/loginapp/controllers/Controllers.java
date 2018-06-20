/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import teem.loginapp.model.dmo.AccountBuilder;
import teem.loginapp.service.CountriesService;
import teem.loginapp.service.SwellrtAccountService;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import teem.loginapp.model.dmo.StrokAttributesMongoDMO;
import teem.loginapp.pojo.LinkedInAuthAccessToken;
import teem.loginapp.service.PropertiesService;
import teem.loginapp.service.StorkAttributeService;
import teem.loginapp.utils.AccountUtils;
import teem.loginapp.utils.Wrappers;

/**
 *
 * @author nikos
 */
@Controller
@CrossOrigin(origins = "*")
public class Controllers {

    Logger LOG = LoggerFactory.getLogger(Controllers.class);

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private SwellrtAccountService accountService;

    @Autowired
    private CountriesService countriesService;

    @Autowired
    private PropertiesService props;

    @Autowired
    private StorkAttributeService attributeService;

    /**
     * @param sp, service provider either sp3 or sp4 or sp5
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView loginView(@RequestParam(value = "sp", required = false) String sp,
            HttpServletRequest request) throws FileNotFoundException, IOException {

        ModelAndView mv = new ModelAndView("login");
        UUID token = UUID.randomUUID();
        mv.addObject("token", token);
        mv.addObject("sp", props.getSP());
        mv.addObject("node", props.getNode());
        mv.addObject("nodePre", props.getPreNode());
        mv.addObject("countries", countriesService.findAll());
        mv.addObject("samlType", props.getSamlType());
        if (Boolean.parseBoolean(props.getProperties().get("mastiha").toString())) {
            mv.addObject("css", "main.css");
            mv.addObject("logo", "app-logo.png");
        } else {
            mv.addObject("css", "main2.css");
            mv.addObject("logo", "logo2.png");
        }

        mv.addObject("natural", Wrappers.getPersonalAttributes(attributeService.getEnabledMng()));
        mv.addObject("legal", Wrappers.getLegalAttributes(attributeService.getEnabledMng()));

        LOG.info("Generated token " + token);
        LOG.info("IP " + request.getRemoteAddr());
        if (cacheManager.getCache("ips").get(request.getRemoteAddr()) != null) {
            cacheManager.getCache("ips").evict(request.getRemoteAddr());
        }
        cacheManager.getCache("ips").put(request.getRemoteAddr(), token);

        String clientID = props.getProperties().getProperty("CLIENT_ID");//System.getenv(CLIENT_ID);
        String redirectURI = props.getProperties().getProperty("REDIRECT_URL");//System.getenv(REDIRECT_URI);
        String responseType = "code";
        String state = UUID.randomUUID().toString();
        mv.addObject("clientID", clientID);
        mv.addObject("redirectURI", redirectURI);
        mv.addObject("responseType", responseType);
        mv.addObject("state", state);
        boolean linkedIn = StringUtils.isEmpty(props.getProperties().getProperty("LINKED_IN")) ? false : Boolean.parseBoolean(props.getProperties().getProperty("LINKED_IN"));
        mv.addObject("linkedIn", linkedIn);

        return mv;
    }

    @RequestMapping(value = {"/createacount", "createaccount"})
    public ModelAndView createAccount(@RequestParam(value = "sp", required = false) String sp,
            HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("createaccount");
        UUID token = UUID.randomUUID();
        mv.addObject("token", token);
        mv.addObject("sp", props.getSP());
        mv.addObject("node", props.getNode());
        mv.addObject("samlType", props.getSamlType());
        mv.addObject("countries", countriesService.findAll());
        if (Boolean.parseBoolean(props.getProperties().get("mastiha").toString())) {
            mv.addObject("css", "main.css");
            mv.addObject("logo", "app-logo.png");
        } else {
            mv.addObject("css", "main2.css");
            mv.addObject("logo", "logo2.png");
        }

        List<StrokAttributesMongoDMO> attrToDisplay = attributeService.getEnabledMng().stream()
                .filter(attr -> {
                    return !attr.getEidasName().toLowerCase().contains("role");
                }).collect(Collectors.toList());

        mv.addObject("natural", Wrappers.getPersonalAttributes(attrToDisplay));
        mv.addObject("legal", Wrappers.getLegalAttributes(attrToDisplay));

        LOG.info("Generated token " + token);
        if (cacheManager.getCache("ips").get(request.getRemoteAddr()) != null) {
            cacheManager.getCache("ips").evict(request.getRemoteAddr());
        }
        cacheManager.getCache("ips").put(request.getRemoteAddr(), token);
        LOG.info("IP " + request.getRemoteAddr());
        LOG.info("TOKEN " + token);

        String clientID = props.getProperties().getProperty("CLIENT_ID");//System.getenv(CLIENT_ID);
        String redirectURI = props.getProperties().getProperty("REDIRECT_URL");//System.getenv(REDIRECT_URI);
        String responseType = "code";
        String state = UUID.randomUUID().toString();
        mv.addObject("clientID", clientID);
        mv.addObject("redirectURI", redirectURI);
        mv.addObject("responseType", responseType);
        mv.addObject("state", state);
        boolean linkedIn = StringUtils.isEmpty(props.getProperties().getProperty("LINKED_IN")) ? false : Boolean.parseBoolean(props.getProperties().getProperty("LINKED_IN"));
        mv.addObject("linkedIn", linkedIn);

        return mv;
    }

    @RequestMapping("/authsuccess")
    public ModelAndView authorizationSuccess(@RequestParam(value = "t", required = true) String token,
            HttpSession httpSession, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) {

        //remove trailing "/" if it is part of the token
        if (token.endsWith("/")) {
            token = token.substring(0, token.length() - 1);
        }
        AccountBuilder.SwellrtAccountMngDMO account = accountService.findByToken(token);
        if (account != null) {
            ModelAndView mv = new ModelAndView("authsuccess");
            mv.addObject("server", props.getServer());
            if (Boolean.parseBoolean(props.getProperties().get("mastiha").toString())) {
                mv.addObject("css", "main.css");
                mv.addObject("logo", "app-logo.png");
            } else {
                mv.addObject("css", "main2.css");
                mv.addObject("logo", "logo2.png");
            }
            httpSession.setAttribute("eId", account.getEid());

            //Service code
            Cookie myCookie
                    = new Cookie("eId", account.getEid());
            myCookie.setPath("/");
            response.addCookie(myCookie);

            return mv;
        }
        attributes.addFlashAttribute("errorMsg", "Please Register before Logging in!");
        attributes.addFlashAttribute("title", "");
        ModelAndView modelAndView = new ModelAndView("redirect:/authfail?t=err");

        return modelAndView;

    }

    @RequestMapping("/authfail")
    public ModelAndView authorizationFail(@RequestParam(value = "t", required = false) String token,
            @RequestParam(value = "reason", required = false) String reason,
            Model model) {
        ModelAndView mv = new ModelAndView("authfail");
        mv.addObject("server", props.getServer());
        if (Boolean.parseBoolean(props.getProperties().get("mastiha").toString())) {
            mv.addObject("css", "main.css");
            mv.addObject("logo", "app-logo.png");
        } else {
            mv.addObject("css", "main2.css");
            mv.addObject("logo", "logo2.png");
        }

        Cache.ValueWrapper errorMsg = token != null ? cacheManager.getCache("errors").get(token) : null;
//        LOG.info("ERROR MESSAGE" + errorMsg.get());

        if (token != null && model.asMap().get("errorMsg") == null) {
            if (errorMsg != null && errorMsg.get() != null) {
                mv.addObject("title", "Registration/Login Cancelled");
                mv.addObject("errorMsg", errorMsg.get());
            } else {
                mv.addObject("title", "Non-sucessful authentication");
                mv.addObject("errorMsg", "Please, return to the home page and re-initialize the process. If the authentication fails again, please contact your national eID provider");
            }
        }

        if (reason != null) {
            if (reason.equals("disagree")) {
                mv.addObject("errorType", "DISAGREE");
            } else {
                mv.addObject("errorType", "CANCEL");
            }
            mv.addObject("title", "");
            mv.addObject("errorMsg", "Registration/Login Cancelled");
        }

        return mv;
    }

    @RequestMapping("/about")
    public ModelAndView about() {
        ModelAndView mv = new ModelAndView("about");
        return mv;
    }

    @RequestMapping("/instructions")
    public ModelAndView instructions() {
        ModelAndView mv = new ModelAndView("instructions");
        return mv;
    }

    @RequestMapping(value = "/linkedInResponse", method = {RequestMethod.POST, RequestMethod.GET})
    public String linkedInResponse(@RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "error_description", required = false) String errorDescription,
            HttpServletResponse httpResponse, Model model, HttpServletRequest httpRequest) {

        //TODO Before you accept the authorization code, your application should ensure that the value returned in the state parameter matches the state value from your original authorization code request.
        if (StringUtils.isEmpty(error)) {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("grant_type", "authorization_code");
            map.add("code", code);
            map.add("redirect_uri", props.getProperties().getProperty("REDIRECT_URL"));
            map.add("client_id", props.getProperties().getProperty("CLIENT_ID")); //System.getenv(CLIENT_ID));
            map.add("client_secret", props.getProperties().getProperty("LINKED_IN_SECRET"));//System.getenv(LINKED_IN_SECRET));

            HttpEntity<MultiValueMap<String, String>> request
                    = new HttpEntity<>(map, headers);

            ResponseEntity<LinkedInAuthAccessToken> response = restTemplate
                    .exchange("https://www.linkedin.com/oauth/v2/accessToken", HttpMethod.POST, request, LinkedInAuthAccessToken.class);

            HttpHeaders headersUser = new HttpHeaders();
            headersUser.setContentType(MediaType.APPLICATION_JSON);
            headersUser.set("Authorization", "Bearer " + response.getBody().getAccess_token());
            HttpEntity<String> entity = new HttpEntity<String>("", headersUser);
            ResponseEntity<String> userResponse
                    = restTemplate.exchange("https://www.linkedin.com/v1/people/~:(id,firstName,lastName,email-address)?format=json", HttpMethod.GET, entity, String.class); //user details https://www.linkedin.com/v1/people/~

            try {
                if (cacheManager.getCache("ips").get(httpRequest.getRemoteAddr()) != null) {
                    cacheManager.getCache("ips").evict(httpRequest.getRemoteAddr());
                }

                String token = UUID.randomUUID().toString();
                //remove trailing "/" if it is part of the token
                if (token.endsWith("/")) {
                    token = token.substring(0, token.length() - 1);
                }
                cacheManager.getCache("ips").put(httpRequest.getRemoteAddr(), token);
                AccountBuilder.SwellrtAccountMngDMO account = Wrappers.wrapLinkedInResponseToAccount(userResponse.getBody());
                LOG.info("LinkedIn Response:: " + userResponse.getBody());
                account.setToken(token);
                account.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());
                if (accountService.findByEid(account.getEid()) == null) {
                    account = AccountUtils.updateAccountId(account, accountService);
                } else {
                    account = accountService.findByEid(account.getEid());
                    account.setToken(token);
                }
                accountService.saveOrUpdate(account);

                //Service code
                Cookie myCookie
                        = new Cookie("eId", account.getEid());
                myCookie.setPath("/");
                httpResponse.addCookie(myCookie);

                model.addAttribute("server", props.getServer());

                return "redirect:authsuccess?t=" + token;

            } catch (Exception e) {
                LOG.info("Exception", e);
            }

        }

        return "authfail";
    }

}
