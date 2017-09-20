/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.serviceImpl;

import gr.ntua.swellrt.model.dao.EmailContentsRepository;
import gr.ntua.swellrt.model.dmo.EmailContentsMngDMO;
import gr.ntua.swellrt.pojo.SwellrtEvent;
import gr.ntua.swellrt.service.MailService;
import java.util.List;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author nikos
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private EmailContentsRepository emailRepo;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    private final static String MAIL_HOST = "localhost";
    private final static String MAIL_FRIENDLY_NAME = "UAegean Online Communities";
    private final static String MAIL_SERVER_FROM = "@aegean.gr";

    private static Logger log = LoggerFactory.getLogger(MailService.class);

    public String prepareAndSend(String recipient, String subject, String userName) {
        mailSender.setHost(MAIL_HOST);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        EmailContentsMngDMO emailContents = emailRepo.findBySubject(subject);
        StringBuilder from = new StringBuilder();
        from.append(emailContents.getUser())
                .append(MAIL_SERVER_FROM);
        try {
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(from.toString(), MAIL_FRIENDLY_NAME));
            helper.setSubject(emailContents.getSubject());

            String content = mailContentBuilder.build(userName);

            helper.setText(content, true);

            mailSender.send(message);

            return "OK";
        } catch (Exception e) {
            log.error("Error sending mail", e.getMessage());
            return "ERROR";
        }
    }

    @Override
    public String sendEventMail(String recipient, SwellrtEvent evt) {
        mailSender.setHost(MAIL_HOST);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        StringBuilder from = new StringBuilder();
        from.append("teem").append(MAIL_SERVER_FROM);

        try {
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(from.toString(), MAIL_FRIENDLY_NAME));
            helper.setSubject(evt.getData().getTitle() + "-" + evt.getData().getSummaryText());
            String content = mailContentBuilder.buildEventContent(evt);
            helper.setText(content, true);
            mailSender.send(message);
            return "OK";
        } catch (Exception e) {
            log.error("Error sending mail", e.getMessage());
            return "ERROR";
        }
    }

    @Override
    public String sendEmailsForEvent(SwellrtEvent event) {
        final StringBuilder response = new StringBuilder();

        if (event != null && event.getData() != null && event.getData().getRecipients() != null) {
            List<String> recipients = event.getData().getRecipients();
            if (recipients.size() > 0) {
                recipients.stream().forEach(email -> {
                    response.setLength(0);
                    response.append(sendEventMail(email, event));
                });
            }
        }
        return response.toString();
    }

}
