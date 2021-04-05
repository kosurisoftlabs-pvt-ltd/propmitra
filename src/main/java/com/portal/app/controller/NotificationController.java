package com.portal.app.controller;

import com.portal.app.payload.ApiResponse;
import com.portal.app.payload.request.InquiryRequest;
import com.portal.app.service.InquiryService;
import com.portal.app.service.VerificationTokenService;
import com.portal.app.util.LoggerUtil;
import org.apache.commons.collections4.MapUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private InquiryService inquiryService;

    @GetMapping("/email-verify")
    public ResponseEntity<?> emailVerification(@RequestParam(value = "email") String email) {
        LoggerUtil.debug(logger, "IN :: emailVerification :: request details : ", email);
        try {
            Boolean response = verificationTokenService.createVerification(email);
            if(response == null) {
                return new ResponseEntity<>(new HashMap<String, String>() {{
                    put("isMailSent", "false");
                    put("mailReason", "Email does not exist");
                }}, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new HashMap<String, String>() {{put("isMailSent", String.valueOf(response));}}, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: emailVerification", null);
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/inquiry")
    public ResponseEntity<?> inquiry(@Valid @RequestBody InquiryRequest request) {

        LoggerUtil.debug(logger, "IN :: inquiry :: request details : ", request);
        try {
            Map<String, String> response = inquiryService.saveAndSendInquiry(request);
            if(MapUtils.isEmpty(response)) {
                LoggerUtil.debug(logger, "OUT :: inquiry :: no data from response : ", response.size());
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            } else {
                LoggerUtil.debug(logger, "OUT :: inquiry :: inquiry status : " + response, null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: inquiry", null);
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
