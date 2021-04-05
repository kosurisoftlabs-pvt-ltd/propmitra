package com.portal.app.service;

import com.portal.app.model.PropertyMaster;
import com.portal.app.repository.PropertyMasterRepository;
import com.portal.app.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyService {

    private static final Logger logger = LoggerFactory.getLogger(VerifyService.class);

    private PropertyMasterRepository repository;
    private MailService mailService;

    @Autowired
    public VerifyService(PropertyMasterRepository repository, MailService mailService) {
        this.repository = repository;
        this.mailService = mailService;
    }

    public void sendVerifyStatus(Long id) {
        LoggerUtil.debug(logger, "IN :: sendVerifyStatus", StringUtils.EMPTY);
        PropertyMaster modelMaster = repository.getOne(id);
        if (modelMaster != null && modelMaster.getId() != null && modelMaster.getId() > 0) {
            LoggerUtil.debug(logger, "Sending notifications to: ", modelMaster.getUser().getEmail() + " and "
                    + modelMaster.getUser().getEmail());
            mailService.sendPropertyVerifyMail(modelMaster.getUser().getName(), modelMaster.getUser().getEmail(), modelMaster.getPropId());
        }
        LoggerUtil.debug(logger, "OUT :: sendVerifyStatus", StringUtils.EMPTY);
    }
}
