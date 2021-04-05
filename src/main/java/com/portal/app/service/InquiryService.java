package com.portal.app.service;

import com.portal.app.model.PropertyInquiry;
import com.portal.app.model.PropertyMaster;
import com.portal.app.payload.request.InquiryRequest;
import com.portal.app.payload.request.SMSRequest;
import com.portal.app.repository.PropertyMasterRepository;
import com.portal.app.util.LoggerUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class InquiryService {

    private static final Logger logger = LoggerFactory.getLogger(InquiryService.class);

    private PropertyMasterRepository repository;
    private MailService mailService;
    private SMSService smsService;

    @Autowired
    public InquiryService(PropertyMasterRepository repository, MailService mailService, SMSService smsService) {
        this.repository = repository;
        this.mailService = mailService;
        this.smsService = smsService;
    }

    public Map<String, String> saveAndSendInquiry(InquiryRequest request) {

        LoggerUtil.debug(logger, "IN :: saveAndSendInquiry", StringUtils.EMPTY);

        HashMap<String, String> resMap = new HashMap<>();
        if (request.getPid() != null && request.getPid() > 0) {

            //check for duplicate inquiries
            if(repository.getInquiryCount(request.getPid(), request.getEmail(), request.getContact()) > 0) {
                resMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
                resMap.put("message", "You have already submitted inquiry request for this property.");
                return resMap;
            }

            PropertyMaster modelMaster = repository.getOne(request.getPid());
            if(modelMaster != null && modelMaster.getId() != null && modelMaster.getId() > 0) {
                LoggerUtil.debug(logger, "Saving the inquiry details", request);
                ModelMapper modelMapper = new ModelMapper();
                PropertyInquiry model = modelMapper.map(request, PropertyInquiry.class);
                model.setPropertyMaster(modelMaster);
                if (CollectionUtils.isNotEmpty(modelMaster.getInquiries())) {
                    modelMaster.getInquiries().add(model);
                } else {
                    Set<PropertyInquiry> inquiries = new HashSet<>();
                    inquiries.add(model);
                    modelMaster.setInquiries(new HashSet<PropertyInquiry>() {{
                        add(model);
                    }});
                }
                repository.save(modelMaster);
                LoggerUtil.debug(logger, "Inquiry details saved successfully", StringUtils.EMPTY);

                LoggerUtil.debug(logger, "Sending notifications to: ", request.getEmail() + " and "
                + modelMaster.getUser().getEmail());
                boolean mailFlag = mailService.sendInquiryMail(request, modelMaster.getUser().getName(), modelMaster.getUser().getEmail(), modelMaster.getPropId());
                if(mailFlag) {
                    LoggerUtil.debug(logger, "Inquiry details sent successfully ", StringUtils.EMPTY);
                    resMap.put("status", String.valueOf(HttpStatus.OK.value()));
                    resMap.put("message", "Thank you for your interest. Authorized property person will contact you soon.");
                } else {
                    logger.error("Unable to send email notifications");
                    resMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
                    resMap.put("message", "Unable to send inquiry details to the property user. Please try again later!");
                }

                LoggerUtil.debug(logger, "Sending SMS to : ", request.getContact()+ " and "
                        + modelMaster.getUser().getContact());
                prepareAndSendSMS(request, modelMaster, resMap);
            } else {
                resMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
                resMap.put("message", "Unable to process your request. Please try again later.");
            }

        } else {
            resMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
            resMap.put("message", "Unable to process your request. Please try again later.");
        }
        return resMap;
    }

    private void prepareAndSendSMS(InquiryRequest request, PropertyMaster modelMaster, HashMap<String, String> resMap) {
        /*String customerMessage = "Hi " + request.getName() + ",\nThanks for showing interest in Propmitra. Your property inquiry request has been received. Our representative will contact you soon. \nThe Propmitra team";
        String ownerMessage = "Hi " + modelMaster.getUser().getName() + ", \n" + request.getName() + " has shown interest in your property - "
                + modelMaster.getPropId() + ". Contact no. " + request.getContact() + "\n Regards, The Propmitra team.";*/

        String customerMessage = "The requested property contact details are\n" +
                "Name : " + modelMaster.getUser().getName() + "\n" +
                "Phone : " + modelMaster.getUser().getContact() + "\n" +
                "Email: " + modelMaster.getUser().getEmail();

        String ownerMessage = "The requested property contact details are\n" +
                "Name : " + request.getName() + "\n" +
                "Phone : " + request.getContact() + "\n" +
                "Email: " + request.getEmail();

        SMSRequest smsRequest = new SMSRequest();
        smsRequest.setCustomerMessage(customerMessage);
        smsRequest.setCustomerNumber(Long.valueOf(request.getContact()));
        smsRequest.setOwnerMessage(ownerMessage);
        smsRequest.setOwnerNumber(Long.valueOf(modelMaster.getUser().getContact()));
        String result = smsService.sendSMS(smsRequest);
        if (result != null && !result.startsWith("Error")) {
            LoggerUtil.debug(logger, "SMS details sent successfully ", StringUtils.EMPTY);
            resMap.put("status", String.valueOf(HttpStatus.OK.value()));
            resMap.put("message", "Thank you for your interest. Authorized property person will contact you soon.");
        } else {
            resMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
            resMap.put("message", "Unable to send SMS. Please contact our customer support!");
        }
    }
}
