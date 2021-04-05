package com.portal.app.service;

import com.portal.app.helper.MailProperties;
import com.portal.app.payload.request.InquiryRequest;
import com.portal.app.util.DateUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    private final MailProperties mailProperties;
    private final Configuration templates;

    @Autowired
    MailService(MailProperties mailProperties, Configuration templates) {
        this.mailProperties = mailProperties;
        this.templates = templates;
    }

    public boolean sendVerificationMail(String name, String toEmail, String verificationCode) {
        String subject = "Propmitra - Verify your email address";
        String body = "";
        try {
            Template t = templates.getTemplate("email-verification.ftl");
            Map<String, String> map = new HashMap<>();
            map.put("USER_NAME", name);
            map.put("VERIFICATION_URL", mailProperties.getVerificationapi() + verificationCode);
            body = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }
        return sendMail(toEmail, subject, body);
    }

    public boolean sendForgotPasswordMail(String name, String toEmail, String verificationCode) {
        String subject = "Reset password for your propmitra account";
        String body = "";
        try {
            Template t = templates.getTemplate("reset-pwd.ftl");
            Map<String, String> map = new HashMap<>();
            map.put("USER_NAME", name);
            map.put("RESETPWD_URL", mailProperties.getForgotpwdapi() + verificationCode);
            body = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }
        return sendMail(toEmail, subject, body);
    }

    public boolean sendInquiryMail(InquiryRequest request, String userName, String toEmail, String propId) {
        if(sendInquiryToCustomer(request, propId) && sendInquiryToUser(request, userName, toEmail, propId)) {
            return true;
        }
        return false;
    }

    public boolean sendPropertyVerifyMail(String userName, String toEmail, String propId) {
        String subject = "Your property has been verified on " + DateUtil.dateToStr(new Date());
        String body = "";
        try {
            Template t = templates.getTemplate("verify-property.ftl");
            Map<String, String> map = new HashMap<>();
            map.put("USER_NAME", userName);
            map.put("PROP_ID", propId);
            body = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }
        return sendMail(toEmail, subject, body);
    }

    private boolean sendInquiryToUser(InquiryRequest request, String userName, String toEmail, String propId) {
        String subject = "Property inquiry request from Sample on " + DateUtil.dateToStr(new Date());
        String body = "";
        try {
            Template t = templates.getTemplate("inquiry-user.ftl");
            Map<String, String> map = new HashMap<>();
            map.put("USER_NAME", userName);
            map.put("PROP_ID", propId);
            map.put("CUSTOMER_NAME", request.getName());
            map.put("EMAIL", request.getEmail());
            map.put("CONTACT", request.getContact());
            map.put("MESSAGE", request.getMessage());
            body = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }
        return sendMail(toEmail, subject, body);
    }

    private boolean sendInquiryToCustomer(InquiryRequest request, String propId) {
        String subject = "Property inquiry request on " + DateUtil.dateToStr(new Date());
        String body = "";
        try {
            Template t = templates.getTemplate("inquiry-customer.ftl");
            Map<String, String> map = new HashMap<>();
            map.put("CUSTOMER_NAME", request.getName());
            map.put("PROP_ID", propId);
            body = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }
        return sendMail(request.getEmail(), subject, body);
    }

    private boolean sendMail(String toEmail, String subject, String body) {
        try {
            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtp.port", mailProperties.getSmtp().getPort());
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);
            session.setDebug(false);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mailProperties.getFrom(), mailProperties.getFromName()));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject(subject);
            msg.setContent(body, "text/html");

            Transport transport = session.getTransport();
            transport.connect(mailProperties.getSmtp().getHost(), mailProperties.getSmtp().getUsername(), mailProperties.getSmtp().getPassword());
            transport.sendMessage(msg, msg.getAllRecipients());
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        return false;
    }
}
