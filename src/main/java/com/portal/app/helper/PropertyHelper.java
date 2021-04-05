package com.portal.app.helper;

import com.portal.app.exception.AppException;
import com.portal.app.model.PropertyMaster;
import com.portal.app.payload.request.BasicInfoRequest;
import com.portal.app.payload.request.FloorRequest;
import com.portal.app.payload.request.SearchRequest;
import com.portal.app.payload.response.*;
import com.portal.app.util.DateUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class PropertyHelper {

    private static final Logger logger = LoggerFactory.getLogger(PropertyHelper.class);
    String[] floorNo = {"1", "2", "3", "4", "5", "6"};
    Random r=new Random();

    public PropertyBasic buildPropertyBasicResponse(PropertyMaster propertyMaster) {
        if(propertyMaster != null && propertyMaster.getPropertyBasic() != null) {
            PropertyBasic response = new ModelMapper().map(propertyMaster.getPropertyBasic(), PropertyBasic.class);
            response.setpId(propertyMaster.getId());
            response.setPropId(propertyMaster.getPropId());
            return response;
        } else {
            throw new AppException("No property or property basic info found");
        }
    }

    public PropertyFloor buildPropertyFloorResponse(PropertyMaster propertyMaster) {
        if(propertyMaster != null && propertyMaster.getPropertyFloor() != null) {
            PropertyFloor response = new ModelMapper().map(propertyMaster.getPropertyFloor(), PropertyFloor.class);
            response.setpId(propertyMaster.getId());
            response.setPropId(propertyMaster.getPropId());
            return response;
        } else {
            throw new AppException("No property or property floor info found");
        }
    }

    public PropertyAmenities buildPropertyAmenitiesResponse(PropertyMaster propertyMaster) {
        if(propertyMaster != null && propertyMaster.getPropertyAmenities() != null) {
            PropertyAmenities response = new ModelMapper().map(propertyMaster.getPropertyAmenities(), PropertyAmenities.class);
            response.setpId(propertyMaster.getId());
            response.setPropId(propertyMaster.getPropId());
            return response;
        } else {
            throw new AppException("No property or amenities info found");
        }
    }

    public PropertyGallery buildPropertyGalleryResponse(PropertyMaster propertyMaster) {
        if(propertyMaster != null && propertyMaster.getPropertyGallery() != null) {
            PropertyGallery response = new ModelMapper().map(propertyMaster.getPropertyGallery(), PropertyGallery.class);
            response.setpId(propertyMaster.getId());
            response.setPropId(propertyMaster.getPropId());
            return response;
        } else {
            throw new AppException("No property or property gallery found");
        }
    }

    public PropertyMaterial buildPropertyMaterialResponse(PropertyMaster propertyMaster) {
        if(propertyMaster != null && propertyMaster.getPropertyMaterial() != null) {
            PropertyMaterial response = new ModelMapper().map(propertyMaster.getPropertyMaterial(), PropertyMaterial.class);
            response.setpId(propertyMaster.getId());
            response.setPropId(propertyMaster.getPropId());
            return response;
        } else {
            throw new AppException("No property or property materials found");
        }
    }

    public PropertyNearby buildPropertyNearbyResponse(PropertyMaster propertyMaster) {
        if(propertyMaster != null && propertyMaster.getPropertyNearBy() != null) {
            PropertyNearby response = new ModelMapper().map(propertyMaster.getPropertyNearBy(), PropertyNearby.class);
            response.setpId(propertyMaster.getId());
            response.setPropId(propertyMaster.getPropId());
            return response;
        } else {
            throw new AppException("No property or property nearby info found");
        }
    }

    public PropertyBanks buildPropertyBanksResponse(PropertyMaster propertyMaster) {
        if(propertyMaster != null && propertyMaster.getPropertyBanks() != null) {
            PropertyBanks response = new ModelMapper().map(propertyMaster.getPropertyBanks(), PropertyBanks.class);
            response.setpId(propertyMaster.getId());
            response.setPropId(propertyMaster.getPropId());
            return response;
        } else {
            throw new AppException("No property or property banks info found");
        }
    }

    public List<SearchCriteria> buildSearchCriteria(SearchRequest searchRequest) {
        List<SearchCriteria> params = null;
        if(searchRequest != null) {
            params = new ArrayList<>();
            if(searchRequest.getKeywords() != null) {
                //implement this condition after adding keywords column in the property master
            }
            if(ArrayUtils.isNotEmpty(searchRequest.getBhk())) {
                for(String bhk: searchRequest.getBhk()) {
                    params.add(new SearchCriteria("noBeds", "propertyFloor", ":", bhk));
                }
            }

            if(ArrayUtils.isNotEmpty(searchRequest.getPropType())) {
                for(String propType: searchRequest.getPropType()) {
                    params.add(new SearchCriteria("propType", "propertyBasic", ":", propType));
                }
            }
        }
        return params;
    }

    public List<PropertyResponse> preparePagedPropertyResponse(Page<PropertyMaster> properties) {
        return properties.map(modelMaster -> {
            //return new ModelMapper().map(modelMaster, PropertyResponse.class);
            PropertyResponse propertyResponse = new PropertyResponse();
            propertyResponse.setId(modelMaster.getId());
            propertyResponse.setIsActive(modelMaster.getIsActive());
            propertyResponse.setIsVerified(modelMaster.getIsVerified());
            propertyResponse.setPropId(modelMaster.getPropId());
            propertyResponse.setProgress(modelMaster.getProgress());
            propertyResponse.setVerifyComments(modelMaster.getVerifyComments());
            propertyResponse.setInactiveComments(modelMaster.getInactiveComments());
            if(modelMaster.getPropertyBasic() != null)
                propertyResponse.setPropertyBasic(new ModelMapper().map(modelMaster.getPropertyBasic(), com.portal.app.payload.response.PropertyBasic.class));
            //propertyResponse.setUserName((modelMaster.getUser() != null) ? modelMaster.getUser().getName() : "");
            return propertyResponse;
        }).getContent();
    }

    public List<PropResponse> preparePagedReportResponse(Page<Object[]> properties) {
        return properties.map(objects -> {
            PropResponse propResponse = new PropResponse();
            propResponse.setId((Long) objects[0]);
            propResponse.setPropId((objects[1] != null) ? (String) objects[1] : null);
            propResponse.setIsActive((objects[2] != null) ? (byte) objects[2] : 0);
            propResponse.setIsVerified((objects[3] != null) ? (byte) objects[3] : 0);
            propResponse.setTotalValue((objects[4] != null) ? (double) objects[4] : 0.0);
            propResponse.setCreatedAt((objects[5] != null) ? DateUtil.dateToUIStr(Date.from((Instant) objects[5])) : null);
            propResponse.setUsername((objects[6] != null) ? (String) objects[6] : null);
            propResponse.setPostedBy((objects[7] != null) ? (String) objects[7] : null);
            propResponse.setRentSale((objects[8] != null) ? (String) objects[8] : null);
            propResponse.setProjectName((objects[9] != null) ? (String) objects[9] : null);
            propResponse.setPropLocation((objects[10] != null) ? (String) objects[10] : null);
            propResponse.setIsGated((objects[11] != null) ? (String) objects[11] : null);
            propResponse.setNoBeds((objects[12] != null) ? (String) objects[12] : null);
            propResponse.setProgress((objects[13] != null) ? (Integer) objects[13] : null);
            return propResponse;
        }).getContent();
    }

    public List<PropertySearchResponse> preaprePagedPropertySearchResponse(Page<PropertyMaster> properties) {
        return properties.map(modelMaster -> {
            PropertySearchResponse searchResponse = new PropertySearchResponse();
            searchResponse.setId(modelMaster.getId());
            searchResponse.setIsActive(modelMaster.getIsActive());
            searchResponse.setIsVerified(modelMaster.getIsVerified());
            searchResponse.setPropId(modelMaster.getPropId());
            searchResponse.setProgress(modelMaster.getProgress());
            if(modelMaster.getPropertyBasic() != null)
                searchResponse.setPropertyBasic(new ModelMapper().map(modelMaster.getPropertyBasic(), com.portal.app.payload.response.PropertyBasic.class));
            if(modelMaster.getPropertyFloor() != null) {
                searchResponse.setAreaSft(modelMaster.getPropertyFloor().getAreaSft());
                searchResponse.setFloorNo(modelMaster.getPropertyFloor().getFloorNo());
                searchResponse.setNoBeds(modelMaster.getPropertyFloor().getNoBeds());
                searchResponse.setNoFloors(modelMaster.getPropertyFloor().getNoFloors());
                searchResponse.setPriceSft(modelMaster.getPropertyFloor().getPriceSft());
            }
            return searchResponse;
        }).getContent();
    }

    public List<PropertySearchResponse> preaprePropertySearchResponse(List<PropertyMaster> properties) {
        return properties.stream().map(modelMaster -> {
            PropertySearchResponse searchResponse = new PropertySearchResponse();
            searchResponse.setId(modelMaster.getId());
            searchResponse.setIsActive(modelMaster.getIsActive());
            searchResponse.setIsVerified(modelMaster.getIsVerified());
            searchResponse.setPropId(modelMaster.getPropId());
            searchResponse.setProgress(modelMaster.getProgress());
            if(modelMaster.getPropertyBasic() != null)
                searchResponse.setPropertyBasic(new ModelMapper().map(modelMaster.getPropertyBasic(), com.portal.app.payload.response.PropertyBasic.class));
            if(modelMaster.getPropertyFloor() != null) {
                searchResponse.setAreaSft(modelMaster.getPropertyFloor().getAreaSft());
                searchResponse.setFloorNo(modelMaster.getPropertyFloor().getFloorNo());
                searchResponse.setNoBeds(modelMaster.getPropertyFloor().getNoBeds());
                searchResponse.setNoFloors(modelMaster.getPropertyFloor().getNoFloors());
                searchResponse.setPriceSft(modelMaster.getPropertyFloor().getPriceSft());
            }
            return searchResponse;
        }).collect(Collectors.toList());
    }

    public BasicInfoRequest prepareBasicInfoRequest(int i) {
        String[] postedBy = {"Agent", "Property Owner", "Developer"};
        String[] baths = {"1", "2", "3", "4"};
        String[] floorSft = {"1090", "1125", "1225", "1600", "1800"};
        double[] rentAmount = {10000, 12000, 13000, 14000, 15000, 20000};
        String[] propAge = {"New", "Old"};
        String[] propType = {"Commercial", "Residential", "Agricultural Land"};
        String[] propStatus = {"Resale", "Upcoming"};
        String[] propLocation = {"Kukatpally, Hyderabad", "Nizampet, Hyderabad", "Anna Nagar, Chennai", "Attibele, Bangalore"};
        String[] pinCode = {"111111", "222222", "333333", "444444", "555555"};
        String[] isGated = {"Y", "N"};
        BasicInfoRequest request = new BasicInfoRequest();
        if(i % 2 == 0) {
            request.setRentSale("Rent");
            request.setLeaseTerm("11 months");
            request.setRentAmount(rentAmount[r.nextInt(propAge.length)]);
            request.setAdvanceAmount(request.getRentAmount() * 11);
            request.setFurnishedOrSemi("Semi Furnished");
            request.setNoBathRooms(baths[r.nextInt(baths.length)]);
            request.setPlacesNear("Near by Place "+i);
            request.setLandMark("Landmark "+i);
            request.setFloorSft(floorSft[r.nextInt(floorSft.length)]);
            request.setFloorNo(floorNo[r.nextInt(floorNo.length)]);
        } else {
            request.setRentSale("Sale");
            request.setPropAge(propAge[r.nextInt(propAge.length)]);
        }
        //request.setPostedBy(postedBy[r.nextInt(postedBy.length)]);
        request.setPropType(propType[r.nextInt(propType.length)]);
        request.setPropStatus(propStatus[r.nextInt(propStatus.length)]);
        request.setPostedBy("Agent");
        request.setPropLocation(propLocation[r.nextInt(propLocation.length)]);
        request.setPinCode(pinCode[r.nextInt(pinCode.length)]);
        request.setIsGated(isGated[r.nextInt(isGated.length)]);
        request.setProjectName("Test Sale Project "+i);
        request.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        return request;
    }

    public FloorRequest prepareFloorRequest(int i) {
        String[] bhk = {"1 BHK", "2 BHK", "3 BHK", "4 BHK", "5 BHK", "6 BHK"};
        double[] area = {1090, 1200, 1225, 1580, 1680, 1800};
        double[] price = {3000, 4500, 5200, 1800, 2400, 3500};
        FloorRequest request = new FloorRequest();
        request.setFloorNo(Integer.valueOf(floorNo[r.nextInt(floorNo.length)]));
        request.setNoBeds(bhk[r.nextInt(bhk.length)]);
        request.setNoFloors(15);
        request.setAreaSft(area[r.nextInt(area.length)]);
        request.setPriceSft(price[r.nextInt(price.length)]);
        return request;
    }

    public List<InquiryResponse> preparePagedInquiryResponse(Page<Object[]> properties, boolean isReport) {
        return properties.map(objects -> {
            InquiryResponse response = new InquiryResponse();
            response.setId((Long) objects[0]);
            response.setpId((objects[1] != null) ? (Long) objects[1] : null);
            response.setPropId((objects[2] != null) ? (String) objects[2] : null);
            response.setName((objects[3] != null) ? (String) objects[3] : null);
            response.setEmail((objects[4] != null) ? (String) objects[4] : null);
            response.setContact((objects[5] != null) ? (String) objects[5] : null);
            response.setMessage((objects[6] != null) ? (String) objects[6] : null);
            response.setCreatedAt((objects[7] != null) ?    DateUtil.dateToUIStr(Date.from((Instant) objects[7])) : null);
            if(isReport)
                response.setUsername((objects[8] != null) ? (String) objects[8] : null);
            return response;
        }).getContent();
    }
}
