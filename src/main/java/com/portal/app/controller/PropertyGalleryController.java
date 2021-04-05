package com.portal.app.controller;

import com.portal.app.payload.ApiResponse;
import com.portal.app.payload.request.FloorRequest;
import com.portal.app.payload.request.GalleryRequest;
import com.portal.app.payload.response.PropertyFloor;
import com.portal.app.payload.response.PropertyGallery;
import com.portal.app.security.CurrentUser;
import com.portal.app.security.UserPrincipal;
import com.portal.app.service.PropertyGalleryService;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/property/gallery")
public class PropertyGalleryController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyGalleryController.class);

    @Autowired
    private PropertyGalleryService service;

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/{propId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> get(@PathVariable(value = "propId") Long propId) {
        LoggerUtil.debug(logger, "IN :: get :: gallery :: propId : ", propId);
        PropertyGallery response = service.getGalleryByPropId(propId);
        if (response == null) {
            logger.error("No data returned from service");
            LoggerUtil.debug(logger, "OUT :: get :: gallery", StringUtils.EMPTY);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        LoggerUtil.debug(logger, "OUT :: get :: gallery", StringUtils.EMPTY);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> processGallery(@CurrentUser UserPrincipal currentUser, @Valid @ModelAttribute GalleryRequest request) {
        LoggerUtil.debug(logger, "IN :: processFloorInfo :: request details : ", request);
        try {
            PropertyGallery response = propertyService.saveToGallery(request, currentUser.getId(), request.getpId());
            if (response == null) {
                logger.error("No data returned from service");
                LoggerUtil.debug(logger, "OUT :: processFloorInfo", null);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            LoggerUtil.debug(logger, "OUT :: processFloorInfo", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: processFloorInfo", null);
            return new ResponseEntity<>(new ApiResponse(false, "Unable process your request!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
