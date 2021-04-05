package com.portal.app.controller;

import com.portal.app.payload.PagedResponse;
import com.portal.app.payload.response.InquiryResponse;
import com.portal.app.payload.response.PropResponse;
import com.portal.app.service.ReportService;
import com.portal.app.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @GetMapping("/propertyby/{postedBy}")
    @PreAuthorize("hasRole('ADMIN')")
    public PagedResponse<PropResponse> getReportByUser(@PathVariable(value = "postedBy") String postedBy,
                                                       @RequestParam(value="type", required = false) String type,
                                                       @RequestParam(value="value", required = false) String value,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return reportService.getReportByUser(postedBy, type, value, page, size);
    }

    @GetMapping("/inquiry/{userType}")
    @PreAuthorize("hasRole('ADMIN')")
    public PagedResponse<InquiryResponse> getInquiriesForUser(@PathVariable(value = "userType") String userType,
                                                              @RequestParam(value="type", required = false) String type,
                                                              @RequestParam(value="value", required = false) String value,
                                                              @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                              @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return reportService.getInquiriesForUser(userType, type, value, page, size);
    }
}
