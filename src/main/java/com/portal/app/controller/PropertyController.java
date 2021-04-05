package com.portal.app.controller;

import com.portal.app.payload.ApiResponse;
import com.portal.app.payload.PagedResponse;
import com.portal.app.payload.request.PropertyRequest;
import com.portal.app.payload.response.InquiryResponse;
import com.portal.app.payload.response.PropResponse;
import com.portal.app.payload.response.PropertyResponse;
import com.portal.app.security.CurrentUser;
import com.portal.app.security.UserPrincipal;
import com.portal.app.service.PropertyService;
import com.portal.app.util.AppConstants;
import com.portal.app.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private PropertyService propertyService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PagedResponse<PropResponse> getPropertiesForUser(@CurrentUser UserPrincipal currentUser,
                                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return propertyService.getPropertiesByUser(currentUser, page, size);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PagedResponse<PropertyResponse> getAllProperties(@CurrentUser UserPrincipal currentUser,
                                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return propertyService.getAllProperties(page, size);
    }

    @PostMapping("/inactive")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> inactiveProperty(@CurrentUser UserPrincipal currentUser,
                                              @Valid @ModelAttribute PropertyRequest propertyRequest,
                                              @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                              @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        LoggerUtil.debug(logger, "IN :: inactiveProperty :: request details : ", propertyRequest);
        try {
            boolean response = propertyService.inactiveProperty(propertyRequest, page, size, currentUser);
            if (!response) {
                logger.error("No data returned from service");
                LoggerUtil.debug(logger, "OUT :: inactiveProperty", null);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            LoggerUtil.debug(logger, "OUT :: inactiveProperty", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: inactiveProperty", null);
            return new ResponseEntity<>(new ApiResponse(false, "Unable process your request!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> verifyProperty(@CurrentUser UserPrincipal currentUser,
                                            @Valid @ModelAttribute PropertyRequest propertyRequest,
                                            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        LoggerUtil.debug(logger, "IN :: verifyProperty :: request details : ", propertyRequest);
        try {
            boolean response = propertyService.verifyProperty(propertyRequest, page, size);
            if (!response) {
                logger.error("Unable to verify properties for given propIds: " + propertyRequest.getPropIds());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            LoggerUtil.debug(logger, "OUT :: verifyProperty", null);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: verifyProperty", null);
            return new ResponseEntity<>(new ApiResponse(false, "Unable process your request!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/inquiry")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PagedResponse<InquiryResponse> getInquiriesForUser(@CurrentUser UserPrincipal currentUser,
                                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return propertyService.getInquiriesByUser(currentUser, page, size);
    }

    @GetMapping("/auto-generate")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> generateProperties(@RequestParam(value = "from", defaultValue = "1") int from,
                                                @RequestParam(value = "to", defaultValue = "10") int to) {
        LoggerUtil.debug(logger, "IN :: generateProperties", null);
        try {
            boolean response = propertyService.generateProperties(from, to);
            if (!response) {
                logger.error("Unable to generate properties");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            LoggerUtil.debug(logger, "OUT :: generateProperties", null);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: generateProperties", null);
            return new ResponseEntity<>(new ApiResponse(false, "Unable process your request!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
