package com.portal.app.controller;

import com.portal.app.payload.ApiResponse;
import com.portal.app.payload.request.BankLoanRequest;
import com.portal.app.payload.response.PropertyBanks;
import com.portal.app.security.CurrentUser;
import com.portal.app.security.UserPrincipal;
import com.portal.app.service.PropertyBanksService;
import com.portal.app.service.PropertyService;
import com.portal.app.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/property/banks")
public class PropertyBanksController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyBanksController.class);

    @Autowired
    private PropertyBanksService service;

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/{propId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> get(@PathVariable(value = "propId") Long propId) {
        LoggerUtil.debug(logger, "IN :: get :: selected banks :: propId : ", propId);
        PropertyBanks response = service.getBanksByPropId(propId);
        if (response == null) {
            logger.error("No data returned from basicService");
            LoggerUtil.debug(logger, "OUT :: get :: selected banks", StringUtils.EMPTY);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        LoggerUtil.debug(logger, "OUT :: get :: selected banks", StringUtils.EMPTY);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> processSelectedBanks(@CurrentUser UserPrincipal currentUser, @ModelAttribute BankLoanRequest request) {
        LoggerUtil.debug(logger, "IN :: processSelectedBanks :: request details : ", request);
        try {
            PropertyBanks response = propertyService.saveBankLoanInfo(request, currentUser.getId(), request.getpId());
            if (response == null) {
                logger.error("No data returned from service");
                LoggerUtil.debug(logger, "OUT :: processSelectedBanks", null);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            LoggerUtil.debug(logger, "OUT :: processSelectedBanks", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: processSelectedBanks", null);
            return new ResponseEntity<>(new ApiResponse(false, "Unable process your request!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
