package com.portal.app.controller;

import com.portal.app.payload.ApiResponse;
import com.portal.app.payload.request.AmenitiesRequest;
import com.portal.app.payload.response.PropertyAmenities;
import com.portal.app.security.CurrentUser;
import com.portal.app.security.UserPrincipal;
import com.portal.app.service.PropertyAmenitiesService;
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
@RequestMapping("/api/property/amenities")
public class PropertyAmenitiesController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyAmenitiesController.class);

    @Autowired
    private PropertyAmenitiesService service;

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/{propId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> get(@PathVariable(value = "propId") Long propId) {
        LoggerUtil.debug(logger, "IN :: get :: amenities :: propId : ", propId);
        PropertyAmenities response = service.getAmenitiesByPropId(propId);
        if (response == null) {
            logger.error("No data returned from basicService");
            LoggerUtil.debug(logger, "OUT :: get :: amenities", StringUtils.EMPTY);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        LoggerUtil.debug(logger, "OUT :: get :: amenities", StringUtils.EMPTY);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> processAmenities(@CurrentUser UserPrincipal currentUser, @ModelAttribute AmenitiesRequest request) {
        LoggerUtil.debug(logger, "IN :: processAmenities :: request details : ", request);
        try {
            PropertyAmenities response = propertyService.saveAmenitiesInfo(request, currentUser.getId(), request.getpId());
            if (response == null) {
                logger.error("No data returned from service");
                LoggerUtil.debug(logger, "OUT :: processAmenities", null);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            LoggerUtil.debug(logger, "OUT :: processAmenities", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: processAmenities", null);
            return new ResponseEntity<>(new ApiResponse(false, "Unable process your request!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
