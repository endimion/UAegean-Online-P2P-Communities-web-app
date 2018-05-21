/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.serviceImpl;

import teem.loginapp.pojo.SwellrtEvent;
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
            + "        <title>Welcome to UAegean P2P Communities</title>\n"
            + "    </head>\n"
            + "    <body>\n"
            + "\n"
            + "        <span th:text=\"${message}\">Hi %1$s,</span> "
            + "\n"
            + "        <p>\n"
            + "           Welcome to UAegean P2P Communities Project Your Teem (deployed by UAegean | i4m Lab) account has been created. Now it will be easier than ever to connect and share content with UAegean Online P2P Communities! \n"
            + "        </p>\n"
            + "\n"
            + "        <p>\n"
            + "            Click <a href=\"http://communities-i4mlab.aegean.gr/\">here</a> to join one of UAegean P2P Communities! \n"
            + "        </p>\n"
            +         " <br/>"
            + "        <p>\n"
            + "            Note: You're receiving this transactional email message because you have been registered with UAegean Online P2P Communities Project through your eID provider (via eIDAS Network). This email is sent from an automated account which is not monitored, so we are not able to respond to replies to this email. \n"
            + "        </p>\n"
            +         " <br/>"
            + "        <p>\n"
            + "           Thank you! The administration team\n"
            + "UAegean Online P2P Communities Project by i4m Lab\n"
            + "        </p>\n"
            +"<p>email: eidapps@atlantis-group.gr </p>"
            + "\n"
            + "        <div>\n"
            + "  (*) The use of eIDAS Network for the authentication into <a href=\"http://communities-i4mlab.aegean.gr\">UAegean Online P2P Communities</a> has been funded by the European Commission (<a href=\"https://ec.europa.eu/inea/en/connecting-europe-facility/cef-teleco\">CEF Programme</a> - Agreement No INEA/CEF/ICT/A2015/1147836)"
            + "        </div>\n"
            + "    </body>\n"
            + "</html>";

    
    
    private final static String eventContent = "<!DOCTYPE html>\n"
            + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.thymeleaf.org\">\n"
            + "    <head>\n"
            + "        <title>\"{0}\"</title>\n"
            + "    </head>\n"
            + "    <body>\n"
            + "        <h5>\"{1}\"</h5> "
            + "\n"
            + "        <p>\n"
            + "          \"{2}\"\n"
            + "        </p>\n"
            + "\n"
//            + "        <p>\n"
//            + "            You're receiving this transactional email message because you have been registered with UAegean \n"
//            + "            Online Community Program through your eID provider (via <a href=\"https://ec.europa.eu/cefdigital/wiki/display/EIDCOOPNET/eIDAS+Cooperation+Network\">\n"
//            + "           eIDAS </a>  infastructure). \n"
//            + "        </p>\n"
//            + "        <p>\n"
//            + "            We’ll only email you if we need to inform you on new tasks and changes to your project.\n"
//            + "        </p>\n"
//            + "\n"
//            + "        <p>\n"
//            + "            This email is sent from an automated account which is not monitored and so we’re unable to respond to replies to this email..\n"
//            + "        </p>\n"
//            + "\n"
//            + "        <p>\n"
//            + "            Thank you,\n"
//            + "            The administration team \n"
//            + "        </p>\n"
//            + "\n"
//            + "        <div>\n"
//            + "          UAegean Online Community Student Program by i4m Lab\n"
//            + "          <ul>\n"
//            + "              <li>Software provided by Teem and SwellRT </li>\n"
//            + "              <li>The use of eIDAS infrastructure for the authentication into this community has been  funded by the European Commission (CEF Programme -AGREEMENT No INEA/CEF/ICT/A2015/1147836)</li>\n"
//            + "          </ul>\n"
//            + "        </div>\n"
            + "    </body>\n"
            + "</html>";

    public String build(String userName,String displayName, String password) {
        return String.format(content, userName,displayName,password);
    }

    public String buildEventContent(SwellrtEvent evt) {
       return MessageFormat.format(eventContent, evt.getData().getTitle(),
               evt.getData().getSummaryText(),
               evt.getData().getMessage());
    }
}
