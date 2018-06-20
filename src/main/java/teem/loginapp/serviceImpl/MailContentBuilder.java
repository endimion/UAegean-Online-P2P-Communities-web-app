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
            + "    <body  style=\"font-family:Arial;    font-size: 16px;\">\n"
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
            + " <br/>"
            + "        <p>\n"
            + "            Note: You're receiving this transactional email message because you have been registered with UAegean Online P2P Communities Project through your eID provider (via eIDAS Network). This email is sent from an automated account which is not monitored, so we are not able to respond to replies to this email. \n"
            + "        </p>\n"
            + " <br/>"
            + "        <p>"
            + "           Thank you! The administration team <br/>"
            + "           UAegean Online P2P Communities Project by i4m Lab<br/>"
            + "           email: eidapps@atlantis-group.gr "
            + "        </p>"
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
            + "    <body  style=\"font-family:Arial;    font-size: 16px;\">\n"
            + "       <p>New Activity Notification!</p> "
            + "        <p>"
            + "           {2} "
            + "        </p>"
            + "        <p>"
            + "            Note: You're receiving this transactional email message because you have been registered with UAegean Online P2P Communities Project through your eID provider (via eIDAS Network). This email is sent from an automated account which is not monitored, so we are not able to respond to replies to this email. \n"
            + "        </p>"
            + "        <p>"
            + "           Thank you! The administration team <br/>"
            + "           UAegean Online P2P Communities Project by i4m Lab<br/>"
            + "           email: eidapps@atlantis-group.gr "
            + "        </p>"
            + "    </body>\n"
            + "</html>";

    public String build(String userName, String displayName, String password) {
        return String.format(content, userName, displayName, password);
    }

    public String buildEventContent(SwellrtEvent evt) {
        return MessageFormat.format(eventContent, evt.getData().getTitle(),
                evt.getData().getSummaryText(),
                evt.getData().getMessage());
    }
}
