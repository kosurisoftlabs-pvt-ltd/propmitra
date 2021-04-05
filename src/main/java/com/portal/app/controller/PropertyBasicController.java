package com.portal.app.controller;

import com.portal.app.payload.ApiResponse;
import com.portal.app.payload.request.BasicInfoRequest;
import com.portal.app.payload.response.PropertyBasic;
import com.portal.app.security.CurrentUser;
import com.portal.app.security.UserPrincipal;
import com.portal.app.service.PropertyBasicService;
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
@RequestMapping("/api/property/basic")
public class PropertyBasicController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyBasicController.class);

    @Autowired
    private PropertyBasicService basicService;

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/{propId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> get(@PathVariable(value = "propId") Long propId) {
        LoggerUtil.debug(logger, "IN :: get :: basic info :: propId : ", propId);
        PropertyBasic response = basicService.getBasicInfoByPropId(propId);
        if (response == null) {
            logger.error("No data returned from basicService");
            LoggerUtil.debug(logger, "OUT :: get :: basic info", StringUtils.EMPTY);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        LoggerUtil.debug(logger, "OUT :: get :: basic info", StringUtils.EMPTY);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> processBasicInfo(@CurrentUser UserPrincipal currentUser, @ModelAttribute BasicInfoRequest request) {
        LoggerUtil.debug(logger, "IN :: processBasicInfo :: request details : ", request);
        try {
            PropertyBasic response = propertyService.savePropertyBasicInfo(request, currentUser.getId(), request.getpId());
            if (response == null) {
                logger.error("No data returned from basicService");
                LoggerUtil.debug(logger, "OUT :: processBasicInfo", null);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            LoggerUtil.debug(logger, "OUT :: processBasicInfo", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: processBasicInfo", null);
            return new ResponseEntity<>(new ApiResponse(false, "Unable process your request!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
