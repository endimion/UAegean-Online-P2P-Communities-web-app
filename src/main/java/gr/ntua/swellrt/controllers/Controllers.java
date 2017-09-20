/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.controllers;

import gr.ntua.swellrt.model.dmo.AccountBuilder;
import gr.ntua.swellrt.model.dmo.CountriesMngDMO;
import gr.ntua.swellrt.model.dmo.PersonSqlDMO;
import gr.ntua.swellrt.service.CountriesService;
import gr.ntua.swellrt.service.EidService;
import gr.ntua.swellrt.service.SwellrtAccountService;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author nikos
 */
@Controller
@CrossOrigin(origins = "*")
public class Controllers {

    Logger LOG = LoggerFactory.getLogger(Controllers.class);
    private final static List<String> countries = Arrays.asList("austria", "belgium", "bulgaria",
            "croatia", "cyprus", "czechrepublic", "denmark", "estonia", "finland", "france", "germany", "greece",
            "hungary", "ireland", "italy", "latvia", "luxembourg", "malta", "netherlands", "poland", "portugal",
            "romania", "slovakia", "spain", "sweden");

//    @Autowired
//    private EidService eidService;
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private SwellrtAccountService accountService;

    @Autowired
    private CountriesService countriesService;

    /**
     * @param sp, service provider either sp3 or sp4 or sp5
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView loginView(@RequestParam(value = "sp", required = true) String sp,
            HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("login");
        UUID token = UUID.randomUUID();
        mv.addObject("token", token);
//        mv.addObject("sp", sp);
        // This was HardCoded to work with the eIDAS 1.2 node
        // ISS only accepts 3 as the sp for teemz
        mv.addObject("sp", "sp3");
        mv.addObject("countries", countriesService.findAll());
        LOG.info("Generated token " + token);
        LOG.info("IP " + request.getRemoteAddr());
        if (cacheManager.getCache("ips").get(request.getRemoteAddr()) != null) {
            cacheManager.getCache("ips").evict(request.getRemoteAddr());
        }
        cacheManager.getCache("ips").put(request.getRemoteAddr(), token);
        return mv;
    }

    @RequestMapping("/swellrt")
    public ModelAndView swellRTView(@RequestParam(value = "t", required = true) String token) {
        LOG.info("The token is:" + token);
        ModelAndView mv = new ModelAndView("swellRt");
        mv.addObject("token", token);
        return mv;
    }

    @RequestMapping(value = {"/createacount"})
    public ModelAndView createAccount(@RequestParam(value = "sp", required = true) String sp,
            HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("createaccount");
        UUID token = UUID.randomUUID();
        mv.addObject("token", token);
        mv.addObject("sp", "sp3");
        mv.addObject("countries", countriesService.findAll());

        LOG.info("Generated token " + token);
        if (cacheManager.getCache("ips").get(request.getRemoteAddr()) != null) {
            cacheManager.getCache("ips").evict(request.getRemoteAddr());
        }
        cacheManager.getCache("ips").put(request.getRemoteAddr(), token);
        LOG.info("IP " + request.getRemoteAddr());
        LOG.info("TOKEN " + token);
        return mv;
    }

    //authsuccess
    @RequestMapping("/authsuccess")
    public ModelAndView authorizationSuccess(@RequestParam(value = "t", required = true) String token,
            HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) {
//        PersonSqlDMO eid = eidService.findByToken(token);
        AccountBuilder.SwellrtAccountMngDMO account = accountService.findByToken(token);
        if (account != null) {
//            String username = eid.getUsername();
//            String password = eid.getPassword();
//            String email = eid.getEmail();
//            String eidentifier = eid.getEid();

            ModelAndView mv = new ModelAndView("authsuccess");
//            mv.addObject("username", username);
//            mv.addObject("password", password);
//            mv.addObject("email", email);
//            mv.addObject("eid",eidentifier);
//            MongoUtils.saveOrUpdate(eid);
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
    public ModelAndView authorizationFail() {
        ModelAndView mv = new ModelAndView("authfail");
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
