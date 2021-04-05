package com.portal.app.service;

import com.portal.app.exception.AppException;
import com.portal.app.helper.PropertyHelper;
import com.portal.app.payload.PagedResponse;
import com.portal.app.payload.response.InquiryResponse;
import com.portal.app.payload.response.PropResponse;
import com.portal.app.repository.PropertyMasterRepository;
import com.portal.app.util.AppConstants;
import com.portal.app.util.CommonUtil;
import com.portal.app.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static com.portal.app.util.AppConstants.*;

@Service
public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    @Autowired
    private PropertyHelper propertyHelper;

    @Autowired
    private PropertyMasterRepository repository;

    public PagedResponse<PropResponse> getReportByUser(String postedBy, String type, String value, int page, int size) {

        CommonUtil.validatePageNumberAndSize(page, size);

        // Retrieve Properties
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Object[]> properties = null;

        if(REPORT_TYPE_AGENT.equalsIgnoreCase(postedBy)) {
            postedBy = REPORT_VAL_AGENT;
        } else if(REPORT_TYPE_OWNER.equalsIgnoreCase(postedBy)) {
            postedBy = REPORT_VAL_OWNER;
        } else if(REPORT_TYPE_DEVELOPER.equalsIgnoreCase(postedBy)) {
            postedBy = REPORT_VAL_DEVELOPER;
        }

        if (AppConstants.SEARCH_TYPE_USER_NAME.equals(type)) {
            properties = repository.findByPostedByAndName(postedBy, value, pageable);
        } else if (AppConstants.SEARCH_TYPE_RANGE.equals(type)) {
            Instant from = null;
            Instant to = null;
            if (StringUtils.isNotBlank(value)) {
                String[] dates = value.split("_");
                try {
                    from = DateUtil.strToInstant(dates[0].trim());
                    to = DateUtil.strToInstant(dates[1].trim());
                    properties = repository.findByPostedByAndDateRange(postedBy, from, to, pageable);
                } catch (ParseException e) {
                    throw new AppException("Invalid date range. Please check start and end dates correctly.", e);
                }
            }
        } else {
            properties = repository.findByPostedBy(postedBy, pageable);
        }

        if (properties != null && properties.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), properties.getNumber(),
                    properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());
        }

        List<PropResponse> propResponseList = propertyHelper.preparePagedReportResponse(properties);

        return new PagedResponse<>(propResponseList, properties.getNumber(),
                properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());
    }

    public PagedResponse<InquiryResponse> getInquiriesForUser(String userType, String type, String value, int page, int size) {

        CommonUtil.validatePageNumberAndSize(page, size);

        // Retrieve Properties
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Object[]> properties = null;

        if(REPORT_TYPE_AGENT.equalsIgnoreCase(userType)) {
            userType = REPORT_VAL_AGENT;
        } else if(REPORT_TYPE_OWNER.equalsIgnoreCase(userType)) {
            userType = REPORT_VAL_OWNER;
        } else if(REPORT_TYPE_DEVELOPER.equalsIgnoreCase(userType)) {
            userType = REPORT_VAL_DEVELOPER;
        }

        if (AppConstants.SEARCH_TYPE_USER_NAME.equals(type)) {
            properties = repository.findInquiriesByUserTypeAndName(userType, value, pageable);
        } else if (AppConstants.SEARCH_TYPE_RANGE.equals(type)) {
            Instant from = null;
            Instant to = null;
            if (StringUtils.isNotBlank(value)) {
                String[] dates = value.split("_");
                try {
                    from = DateUtil.strToInstant(dates[0].trim());
                    to = DateUtil.strToInstant(dates[1].trim());
                    properties = repository.findInquiriesByDateRange(userType, from, to, pageable);
                } catch (ParseException e) {
                    throw new AppException("Invalid date range. Please check start and end dates correctly.", e);
                }
            }
        } else {
            properties = repository.findInquiriesByUserType(userType, pageable);
        }

        if (properties != null && properties.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), properties.getNumber(),
                    properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());
        }

        List<InquiryResponse> propResponseList = propertyHelper.preparePagedInquiryResponse(properties, true);

        return new PagedResponse<>(propResponseList, properties.getNumber(),
                properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());
    }
}
