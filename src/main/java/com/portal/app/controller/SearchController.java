package com.portal.app.controller;

import com.portal.app.payload.ApiResponse;
import com.portal.app.payload.PagedResponse;
import com.portal.app.payload.request.SearchRequest;
import com.portal.app.payload.response.PropertySearchResponse;
import com.portal.app.security.CurrentUser;
import com.portal.app.security.UserPrincipal;
import com.portal.app.service.SearchService;
import com.portal.app.util.AppConstants;
import com.portal.app.util.LoggerUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/search")
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @PostMapping("/property")
    public PagedResponse<PropertySearchResponse> searchProperties(@RequestBody SearchRequest request,
                                                                  @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        LoggerUtil.debug(logger, "IN :: searchProperties :: request details : ", request);
        PagedResponse<PropertySearchResponse> response = searchService.searchPagedProperties(request, page, size);
        LoggerUtil.debug(logger, "OUT :: searchProperties :: search results count : ", response.getSize());
        return response;
    }

    @GetMapping ("/type")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> searchPropertiesByType(@CurrentUser UserPrincipal currentUser,
                                              @RequestParam(value = "type") String type,
                                              @RequestParam(value = "value") String value) {
        LoggerUtil.debug(logger, "IN :: searchPropertiesByType :: request details : ", type + "==>" + value);
        try {
            List<PropertySearchResponse> response = searchService.searchPropertiesByType(currentUser, type, value);
            if(CollectionUtils.isNotEmpty(response)) {
                LoggerUtil.debug(logger, "OUT :: searchPropertiesByType :: search results count : ", response.size());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                LoggerUtil.debug(logger, "OUT :: searchPropertiesByType :: No results found : ", null);
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }

        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: searchPropertiesByType", null);
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping ("/properties")
    public String searchProperties(@RequestBody SearchRequest request,
                                 @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                 @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size, Model model) {
        model.addAttribute("results", searchService.searchProperties(request));
        //model.addAttribute("meta", searchService.getSearchMeta((StringUtils.isNotBlank(request.getRentOrSale()) ? request.getRentOrSale() : "Sale")));
        return "results :: results";
    }

    /*@GetMapping ("/property-info/{propId}")
    public String searchPropertiesView(@PathVariable String propId, Model model) {
        model.addAttribute("prop", searchService.getPropertyInfo(propId));
        model.addAttribute("title", "Property Info");
        //model.addAttribute("meta", searchService.getSearchMeta("Sale"));
        //model.addAttribute("filters", new SearchRequest());
        return "property-info";
    }*/

    @GetMapping ("/properties")
    public String searchPropertiesView(Model model) {
        model.addAttribute("results", searchService.searchProperties(new SearchRequest()));
        model.addAttribute("title", "Show Properties");
        model.addAttribute("meta", searchService.getSearchMeta("Sale"));
        //model.addAttribute("filters", new SearchRequest());
        return "properties";
    }

    @GetMapping ("/meta/{rentOrSale}")
    public String getSearchMeta(@PathVariable String rentOrSale, Model model) {
        LoggerUtil.debug(logger, "IN :: getSearchMeta {}", rentOrSale);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setRentOrSale(rentOrSale);
        //model.addAttribute("results", searchService.searchProperties(searchRequest));
        model.addAttribute("results", null);
        model.addAttribute("title", "Show Properties");
        model.addAttribute("meta", searchService.getSearchMeta(rentOrSale));
        LoggerUtil.debug(logger, "OUT :: getSearchMeta", StringUtils.EMPTY);
        return "content";
    }

}
