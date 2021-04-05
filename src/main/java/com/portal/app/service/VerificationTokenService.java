package com.portal.app.service;

import com.portal.app.model.User;
import com.portal.app.model.VerificationToken;
import com.portal.app.payload.request.SMSRequest;
import com.portal.app.repository.UserRepository;
import com.portal.app.repository.VerificationTokenRepository;
import com.portal.app.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.portal.app.model.VerificationToken.STATUS_PENDING;

@Service
public class VerificationTokenService {

    private static final Logger logger = LoggerFactory.getLogger(VerificationTokenService.class);

    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private MailService mailService;
    private SMSService smsService;

    @Autowired
    public VerificationTokenService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, MailService mailService, SMSService smsService) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.smsService = smsService;
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    public Boolean createVerification(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            return null;
        }

        Optional<VerificationToken> tokenOptional = verificationTokenRepository.findByUserEmail(email);
        VerificationToken verificationToken;
        if (!tokenOptional.isPresent()) {
            verificationToken = new VerificationToken();
            verificationToken.setUser(user);
            verificationTokenRepository.save(verificationToken);
        } else {
            verificationToken = tokenOptional.get();
        }

        if (mailService.sendVerificationMail(user.getName(), email, verificationToken.getToken())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean createForgotVerification(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            return null;
        }

        VerificationToken verificationToken;
        Optional<VerificationToken> tokenOptional = verificationTokenRepository.findByUserEmail(email);
        if (!tokenOptional.isPresent()) {
            verificationToken = new VerificationToken();
            verificationToken.setUser(user);

        } else {
            verificationToken = tokenOptional.get();
            verificationToken.setToken(UUID.randomUUID().toString());
            verificationToken.setStatus(STATUS_PENDING);
        }
        verificationTokenRepository.save(verificationToken);
        if (mailService.sendForgotPasswordMail(user.getName(), email, verificationToken.getToken())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public boolean verifyToken(String token) {

        LoggerUtil.debug(logger, "IN :: verifyToken : validating token", StringUtils.EMPTY);
        /* verify whether token is valid or not */
        Optional<VerificationToken> tokenOptional = verificationTokenRepository.findByToken(token);
        if (!tokenOptional.isPresent()) {
            logger.error("Invalid token provided for verification");
            return false;
        }

        VerificationToken verificationToken = tokenOptional.get();

        /* verify whether token has already used or not */
        if(VerificationToken.STATUS_VERIFIED.equalsIgnoreCase(verificationToken.getStatus())) {
            LoggerUtil.debug(logger, "Token already been used.", StringUtils.EMPTY);
            return false;
        }

        /* activate user account*/
        verificationToken.setStatus(VerificationToken.STATUS_VERIFIED);
        verificationToken.getUser().setIsActive((byte) 1);
        verificationTokenRepository.save(verificationToken);
        LoggerUtil.debug(logger, "OUT :: verifyToken : user account activated", StringUtils.EMPTY);
        return true;
    }

    public boolean verifyResetToken(String token) {

        LoggerUtil.debug(logger, "IN :: verifyResetToken : validating token", StringUtils.EMPTY);
        /* verify whether token is valid or not */
        Optional<VerificationToken> tokenOptional = verificationTokenRepository.findByToken(token);
        if (!tokenOptional.isPresent()) {
            logger.error("Invalid token provided for password reset");
            return false;
        }

        VerificationToken verificationToken = tokenOptional.get();

        /* verify whether token has already used or not */
        if(VerificationToken.STATUS_VERIFIED.equalsIgnoreCase(verificationToken.getStatus())) {
            LoggerUtil.debug(logger, "Token already been used.", StringUtils.EMPTY);
            return false;
        }

        /* activate user account*/
        verificationToken.setStatus(VerificationToken.STATUS_VERIFIED);
        verificationTokenRepository.save(verificationToken);
        LoggerUtil.debug(logger, "OUT :: verifyResetToken : validation success", StringUtils.EMPTY);
        return true;
    }

    public boolean resetPassword(String token, String upass) {

        LoggerUtil.debug(logger, "IN :: resetPassword", StringUtils.EMPTY);
        /* verify whether token is valid or not */
        Optional<VerificationToken> tokenOptional = verificationTokenRepository.findByToken(token);
        if (!tokenOptional.isPresent()) {
            logger.error("Invalid token provided for password reset");
            return false;
        }

        VerificationToken verificationToken = tokenOptional.get();

        /* reset user password*/
        verificationToken.setStatus(VerificationToken.STATUS_VERIFIED);
        verificationToken.getUser().setPassword(passwordEncoder.encode(upass));
        verificationTokenRepository.save(verificationToken);
        LoggerUtil.debug(logger, "OUT :: resetPassword : password reset success", StringUtils.EMPTY);
        return true;
    }

    public Boolean createOTPVerification(String contact) {
        Optional<User> optionalUser = userRepository.findByContact(contact);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            return null;
        }

        Optional<VerificationToken> tokenOptional = verificationTokenRepository.findByContact(contact);
        VerificationToken verificationToken;
        if (!tokenOptional.isPresent()) {
            verificationToken = new VerificationToken(String.format("%04d", new Random().nextInt(10000)));
            verificationToken.setUser(user);
            verificationTokenRepository.save(verificationToken);
        } else {
            verificationToken = tokenOptional.get();
        }

        String customerMessage = "Your One Time Password for Propmitra registarion is: " + contact;

        SMSRequest smsRequest = new SMSRequest();
        smsRequest.setCustomerMessage(customerMessage);
        smsRequest.setCustomerNumber(Long.valueOf(contact));
        String result = smsService.sendSMS(smsRequest);

        if (result != null && !result.startsWith("Error")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
