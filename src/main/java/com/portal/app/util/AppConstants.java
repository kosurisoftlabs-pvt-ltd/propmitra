package com.portal.app.util;

import java.util.Arrays;
import java.util.List;

public interface AppConstants {

    String DEFAULT_PAGE_NUMBER = "0";

    String DEFAULT_PAGE_SIZE = "5";

    int MAX_PAGE_SIZE = 50;

    String KEY_IS_VERIFIED = "isVerified";

    String COMMENT_TYPE_VERIFY = "VERIFY";

    String VERIFY_COMMENTS = "verifyComments";

    String COMMENT_TYPE_INACTIVE = "INACTIVE";

    String KEY_IS_ACTIVE = "isActive";

    String INACTIVE_COMMENTS = "inactiveComments";

    String ROLE_ADMIN = "ROLE_ADMIN";

    String ROLE_USER = "ROLE_USER";

    String SEARCH_TYPE_ID = "propId";

    String SEARCH_TYPE_USER_NAME = "username";

    String SEARCH_TYPE_RANGE = "dateRange";

    String PROP_TYPE_SALE = "Sale";

    String PROP_TYPE_RENT = "Rent";

    String APPROVE_USER = "approved";

    String REJECT_USER = "rejected";

    String REPORT_TYPE_AGENT = "agent";
    String REPORT_VAL_AGENT = "Agent";

    String REPORT_TYPE_OWNER = "owner";
    String REPORT_VAL_OWNER = "Property Owner";

    String REPORT_TYPE_DEVELOPER = "developer";
    String REPORT_VAL_DEVELOPER = "Developer";

    List<String> PROPERTY_SUB_LIST = Arrays.asList("propertyBasic", "propertyFloor", "propertyAmenities", "propertyGallery", "propertyMaterial", "propertyNearBy", "propertyBanks");

}
