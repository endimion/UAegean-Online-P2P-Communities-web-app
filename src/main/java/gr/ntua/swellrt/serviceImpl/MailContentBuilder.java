/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.serviceImpl;

import java.text.MessageFormat;
import org.springframework.stereotype.Service;


/**
 *
 * @author nikos
 */
@Service
public class MailContentBuilder {

//    @Autowired
//    private TemplateEngine templateEngine;
    private final static String content = "<!DOCTYPE html>\n"
            + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.thymeleaf.org\">\n"
            + "    <head>\n"
            + "        <title>Registration email</title>\n"
            + "    </head>\n"
            + "    <body>\n"
            + "        <!--<span th:text=\"${message}\"></span>-->\n"
            + "\n"
            + "        <span th:text=\"${message}\">%s</span> "
            + "\n"
            + "        <p>\n"
            + "            Welcome to our community. Your Teem (deployed by UAegean) account has been created. \n"
            + "            Now it will be easier than ever to share and connect with your community. \n"
            + "        </p>\n"
            + "\n"
            + "        <p>\n"
            + "            Click <a href=\"http://community.mastihawonder.com/home/teems\">here</a> to join our community. \n"
            + "        </p>\n"
            + "        <p>\n"
            + "            You're receiving this transactional email message because you have been registered with UAegean \n"
            + "            Online Community Program through your eID provider (via <a href=\"https://ec.europa.eu/cefdigital/wiki/display/EIDCOOPNET/eIDAS+Cooperation+Network\">\n"
            + "           eIDAS </a>  infastructure). \n"
            + "        </p>\n"
            + "        <p>\n"
            + "            We’ll only email you if we need to inform you on new tasks and changes to your project.\n"
            + "        </p>\n"
            + "\n"
            + "        <p>\n"
            + "            This email is sent from an automated account which is not monitored and so we’re unable to respond to replies to this email..\n"
            + "        </p>\n"
            + "\n"
            + "        <p>\n"
            + "            Thank you,\n"
            + "            The administration team \n"
            + "        </p>\n"
            + "\n"
            + "        <div>\n"
            + "          UAegean Online Community Student Program by i4m Lab\n"
            + "          <ul>\n"
            + "              <li>Software provided by Teem and SwellRT </li>\n"
            + "              <li>The use of eIDAS infrastructure for the authentication into this community has been  funded by the European Commission (CEF Programme -AGREEMENT No INEA/CEF/ICT/A2015/1147836)</li>\n"
            + "          </ul>\n"
            + "        </div>\n"
            + "    </body>\n"
            + "</html>";

    public String build(String userName) {
//        Context context = new Context();
//        context.setVariable("userName", userName);
//        return templateEngine.process("mailTemplate", context);
        return String.format(content, userName);
//        return null;
    }
}
