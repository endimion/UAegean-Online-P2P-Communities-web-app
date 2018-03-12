/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import teem.loginapp.model.dmo.AccountBuilder;
import teem.loginapp.service.CountriesService;
import teem.loginapp.service.SwellrtAccountService;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import teem.loginapp.service.PropertiesService;
import teem.loginapp.service.StorkAttributeService;
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
        return mv;
    }

    @RequestMapping(value = {"/createacount"})
    public ModelAndView createAccount(@RequestParam(value = "sp", required = true) String sp,
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

        mv.addObject("natural", Wrappers.getPersonalAttributes(attributeService.getEnabledMng()));
        mv.addObject("legal", Wrappers.getLegalAttributes(attributeService.getEnabledMng()));

        LOG.info("Generated token " + token);
        if (cacheManager.getCache("ips").get(request.getRemoteAddr()) != null) {
            cacheManager.getCache("ips").evict(request.getRemoteAddr());
        }
        cacheManager.getCache("ips").put(request.getRemoteAddr(), token);
        LOG.info("IP " + request.getRemoteAddr());
        LOG.info("TOKEN " + token);
        return mv;
    }

    @RequestMapping("/authsuccess")
    public ModelAndView authorizationSuccess(@RequestParam(value = "t", required = true) String token,
            HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) {
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

        ModelAndView modelAndView = new ModelAndView("redirect:/authfail");
        return modelAndView;

    }

    @RequestMapping("/authfail")
    public ModelAndView authorizationFail(@RequestParam(value = "t", required = false) String token) {
        ModelAndView mv = new ModelAndView("authfail");
        mv.addObject("server", props.getServer());
        if (Boolean.parseBoolean(props.getProperties().get("mastiha").toString())) {
            mv.addObject("css", "main.css");
            mv.addObject("logo", "app-logo.png");
        } else {
            mv.addObject("css", "main2.css");
            mv.addObject("logo", "logo2.png");
        }

        if (token != null) {
            Cache.ValueWrapper errorMsg = cacheManager.getCache("errors").get(token);
            if (errorMsg != null && errorMsg.get() != null) {
                mv.addObject("errorMsg", errorMsg.get());
            } else {
                mv.addObject("errorMsg", "An unexpected error occured! Please, return to the home page and reauthorize the application, using the eIDAS system.");
            }
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

}
