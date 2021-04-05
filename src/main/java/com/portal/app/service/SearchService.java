package com.portal.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.app.dao.SearchDAO;
import com.portal.app.exception.AppException;
import com.portal.app.helper.PropertyHelper;
import com.portal.app.helper.SearchCriteria;
import com.portal.app.model.PropertyMaster;
import com.portal.app.payload.PagedResponse;
import com.portal.app.payload.request.SearchRequest;
import com.portal.app.payload.response.PropertyMetaResponse;
import com.portal.app.payload.response.PropertyResponse;
import com.portal.app.payload.response.PropertySearchResponse;
import com.portal.app.repository.PropertyMasterRepository;
import com.portal.app.security.UserPrincipal;
import com.portal.app.util.AppConstants;
import com.portal.app.util.CommonUtil;
import com.portal.app.util.DateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    private PropertyHelper propertyHelper;

    @Autowired
    private SearchDAO searchRepository;

    @Autowired
    private PropertyMasterRepository propertyRepository;

    public PagedResponse<PropertySearchResponse> searchPagedProperties(SearchRequest searchRequest, int page, int size) {

        CommonUtil.validatePageNumberAndSize(page, size);

        // Retrieve Properties
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<PropertyMaster> properties = searchRepository.searchPagedProperties(searchRequest, pageable);

        if (properties.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), properties.getNumber(),
                    properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());
        }

        List<PropertySearchResponse> searchResults = propertyHelper.preaprePagedPropertySearchResponse(properties);

        return new PagedResponse<>(searchResults, 0, searchResults.size(), searchResults.size(), 1, true);

        /*return new PagedResponse<>(searchResults, properties.getNumber(),
                properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());*/
    }

    public List<PropertySearchResponse> searchProperties(SearchRequest searchRequest) {


        List<PropertyMaster> properties = searchRepository.searchProperties(searchRequest);

        if (CollectionUtils.isEmpty(properties)) {
            return Collections.emptyList();
        }

        return propertyHelper.preaprePropertySearchResponse(properties);

        /*return new PagedResponse<>(searchResults, properties.getNumber(),
                properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());*/
    }

    public PropertyResponse getPropertyInfo(String propId) {
        Optional<PropertyMaster> optionalMaster = propertyRepository.findByPropId(propId);
        if(optionalMaster.isPresent()) {
            return new ModelMapper().map(optionalMaster.get(), PropertyResponse.class);
        }

        return new PropertyResponse();
    }

    public List<PropertySearchResponse> searchPropertiesByType(UserPrincipal currentUser, String type, String value) {
        List<PropertySearchResponse> responseList = null;
        if(CollectionUtils.isNotEmpty(currentUser.getAuthorities())) {
            String role = currentUser.getAuthorities().iterator().next().getAuthority();
            if (AppConstants.SEARCH_TYPE_ID.equals(type)) {
                responseList = serachByPropId(role, value, currentUser.getId());
            } else if (AppConstants.SEARCH_TYPE_RANGE.equals(type)) {
                responseList = serachByDateRange(role, value, currentUser.getId());
            }

        }
        return responseList;
    }

    public Set<String> getKeywordMeta() {
        return searchRepository.findKeywordMeta();
    }

    public PropertyMetaResponse getSearchMeta(String rentOrSale) {
        //1. Get keyword meta
        //Set<String> keyMeta = searchRepository.findKeywordMeta();


        //PropertyMetaResponse response = new PropertyMetaResponse();
        //response.setKeywords(keyMeta);
        return searchRepository.findSearchMeta(rentOrSale);
    }

    private List<PropertySearchResponse> serachByPropId(String role, String value, Long userId) {
        List<PropertySearchResponse> responseList = null;
        PropertyMaster modelMaster = null;
        if (AppConstants.ROLE_ADMIN.equals(role)) {
            try {
                modelMaster = searchRepository.findByPropertyId(value);
            } catch (Exception ne) {
                throw new AppException("No results found for given property id. Make sure the property id provided is correct.", ne);
            }
        } else if (AppConstants.ROLE_USER.equals(role)) {
            try {
                modelMaster = searchRepository.findByUserAndPropId(value, userId);
            } catch (Exception ne) {
                throw new AppException("No results found for given property id. Make sure the property id provided is yours.", ne);
            }
        }
        if (modelMaster != null) {
            PropertySearchResponse searchResponse = new PropertySearchResponse();
            searchResponse.setId(modelMaster.getId());
            searchResponse.setIsActive(modelMaster.getIsActive());
            searchResponse.setIsVerified(modelMaster.getIsVerified());
            searchResponse.setPropId(modelMaster.getPropId());
            searchResponse.setProgress(modelMaster.getProgress());
            searchResponse.setCreatedAt(DateUtil.dateToUIStr(Date.from(modelMaster.getCreatedAt())));
            if(modelMaster.getPropertyBasic() != null)
                searchResponse.setPropertyBasic(new ModelMapper().map(modelMaster.getPropertyBasic(), com.portal.app.payload.response.PropertyBasic.class));
            responseList = new ArrayList<>();
            responseList.add(searchResponse);
        }
        return responseList;
    }

    private List<PropertySearchResponse> serachByDateRange(String role, String value, Long userId) {
        List<PropertySearchResponse> responseList = null;
        List<PropertyMaster> masterList = null;
        Instant from = null;
        Instant to = null;
        if (StringUtils.isNotBlank(value)) {
            String[] dates = value.split("_");
            try {
                from = DateUtil.strToInstant(dates[0].trim());
                to = DateUtil.strToInstant(dates[1].trim());
            } catch (ParseException e) {
                throw new AppException("Invalid date range. Please check start and end dates correctly.", e);
            }
        }
        if (AppConstants.ROLE_ADMIN.equals(role)) {
            try {
                masterList = searchRepository.findPropertiesByDateRange(from, to);
            } catch (Exception ne) {
                throw new AppException("No results found for given date range. Make sure date range provided is correct.", ne);
            }
        } else if (AppConstants.ROLE_USER.equals(role)) {
            try {
                masterList = searchRepository.findPropertiesByDateRange(from, to, userId);
            } catch (Exception ne) {
                throw new AppException("No results found for given date range. Make sure date range provided is correct.", ne);
            }
        }

        if (CollectionUtils.isNotEmpty(masterList)) {
            responseList = masterList.stream().map(modelMaster -> {
                PropertySearchResponse searchResponse = new PropertySearchResponse();
                searchResponse.setId(modelMaster.getId());
                searchResponse.setIsActive(modelMaster.getIsActive());
                searchResponse.setIsVerified(modelMaster.getIsVerified());
                searchResponse.setPropId(modelMaster.getPropId());
                searchResponse.setProgress(modelMaster.getProgress());
                searchResponse.setCreatedAt(DateUtil.dateToUIStr(Date.from(modelMaster.getCreatedAt())));
                if(modelMaster.getPropertyBasic() != null) {
                    searchResponse.setPropertyBasic(new ModelMapper().map(modelMaster.getPropertyBasic(), com.portal.app.payload.response.PropertyBasic.class));
                }
                if (modelMaster.getPropertyFloor() != null) {
                    searchResponse.setAreaSft(modelMaster.getPropertyFloor().getAreaSft());
                    searchResponse.setFloorNo(modelMaster.getPropertyFloor().getFloorNo());
                    searchResponse.setNoBeds(modelMaster.getPropertyFloor().getNoBeds());
                    searchResponse.setNoFloors(modelMaster.getPropertyFloor().getNoFloors());
                    searchResponse.setPriceSft(modelMaster.getPropertyFloor().getPriceSft());
                }
                return searchResponse;
            }).collect(Collectors.toList());
        }
        return responseList;
    }
}
