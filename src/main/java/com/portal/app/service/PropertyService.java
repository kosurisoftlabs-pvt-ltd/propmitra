package com.portal.app.service;

import com.portal.app.dao.PropertyDAO;
import com.portal.app.exception.AppException;
import com.portal.app.helper.PropertyHelper;
import com.portal.app.model.*;
import com.portal.app.payload.PagedResponse;
import com.portal.app.payload.request.*;
import com.portal.app.payload.response.InquiryResponse;
import com.portal.app.payload.response.PropResponse;
import com.portal.app.payload.response.PropertyNearby;
import com.portal.app.payload.response.PropertyResponse;
import com.portal.app.repository.PropertyMasterRepository;
import com.portal.app.repository.UserRepository;
import com.portal.app.security.UserPrincipal;
import com.portal.app.util.AppConstants;
import com.portal.app.util.CommonUtil;
import com.portal.app.util.LoggerUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class PropertyService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    @Autowired
    private PropertyMasterRepository propertyMasterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyHelper propertyHelper;

    @Autowired
    private PropertyDAO propertyDAO;

    public PagedResponse<PropResponse> getPropertiesByUser(UserPrincipal currentUser, int page, int size) {

        CommonUtil.validatePageNumberAndSize(page, size);

        // Retrieve Properties
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Object[]> properties = propertyMasterRepository.findByCreatedByUser(currentUser.getId(), pageable);

        if (properties != null && properties.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), properties.getNumber(),
                    properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());
        }

        List<PropResponse> propResponseList = propertyHelper.preparePagedReportResponse(properties);

        return new PagedResponse<>(propResponseList, properties.getNumber(),
                properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());
    }

    public PagedResponse<PropertyResponse> getPropertiesForUser(UserPrincipal currentUser, int page, int size) {

        CommonUtil.validatePageNumberAndSize(page, size);

        // Retrieve Properties
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<PropertyMaster> properties = propertyMasterRepository.findByCreatedBy(currentUser.getId(), pageable);

        if (properties.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), properties.getNumber(),
                    properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());
        }

        List<PropertyResponse> propertyResponseList = propertyHelper.preparePagedPropertyResponse(properties);

        return new PagedResponse<>(propertyResponseList, properties.getNumber(),
                properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());
    }

    public PagedResponse<PropertyResponse> getAllProperties(int page, int size) {

        CommonUtil.validatePageNumberAndSize(page, size);

        // Retrieve Properties
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<PropertyMaster> properties = propertyMasterRepository.findAll(pageable);

        if (properties.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), properties.getNumber(),
                    properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());
        }

        List<PropertyResponse> propertyResponseList = propertyHelper.preparePagedPropertyResponse(properties);

        return new PagedResponse<>(propertyResponseList, properties.getNumber(),
                properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());
    }

    @Transactional
    public com.portal.app.payload.response.PropertyBasic savePropertyBasicInfo(BasicInfoRequest request, Long userId, Long propId) {
        LoggerUtil.debug(logger, "IN :: savePropertyBasicInfo", null);
        PropertyMaster modelMaster;

        if(propId != null && propId > 0) {
            //update property basic details
            modelMaster = propertyMasterRepository.getOne(propId);
        } else {
            //add property basic details
            modelMaster = createMasterData(userId);
        }
        ModelMapper modelMapper = new ModelMapper();
        if(modelMaster != null && modelMaster.getId() != null && modelMaster.getId() > 0) {
            LoggerUtil.debug(logger, "Updating property basic details for propId : ", modelMaster.getId());
            if(modelMaster.getPropertyBasic() != null) {
                request.setId(modelMaster.getPropertyBasic().getId());
            }
            PropertyBasic propertyBasic = modelMapper.map(request, PropertyBasic.class);
            if(propertyBasic.getRentAmount() != null) {
                modelMaster.setTotalValue(propertyBasic.getRentAmount());
            }
            if(request.getFile() != null) {
                Attachment attachment = new Attachment();
                try {
                    attachment.setData(request.getFile().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                attachment.setFileName(request.getFile().getName());
                attachment.setFileType(request.getFile().getContentType());
                propertyBasic.setFrontImage(attachment);
            } else if(modelMaster.getPropertyBasic() != null && modelMaster.getPropertyBasic().getFrontImage() != null) {
                propertyBasic.setFrontImage(modelMaster.getPropertyBasic().getFrontImage());
            }
            modelMaster.setPropertyBasic(propertyBasic);
            modelMaster.setProgress(modelMaster.getProfilePercentage());
            modelMaster = propertyMasterRepository.save(modelMaster);
        }
        LoggerUtil.debug(logger, "OUT :: savePropertyBasicInfo", null);
        return propertyHelper.buildPropertyBasicResponse(modelMaster);
    }

    @Transactional
    public com.portal.app.payload.response.PropertyFloor savePropertyFloorInfo(FloorRequest request, Long userId, Long propId) {
        LoggerUtil.debug(logger, "IN :: savePropertyFloorInfo", null);
        PropertyMaster modelMaster = null;
        Set<Attachment> existingAttachments = new HashSet<>();
        if(propId != null && propId > 0) {
            //update floor details
            modelMaster = propertyMasterRepository.getOne(propId);
        } else {
            //add foor details
            modelMaster = createMasterData(userId);
        }

        ModelMapper modelMapper = new ModelMapper();
        if(modelMaster != null && modelMaster.getId() != null && modelMaster.getId() > 0) {
            LoggerUtil.debug(logger, "Updating floor info for propId : ", modelMaster.getId());
            if(modelMaster.getPropertyFloor() != null) {
                request.setId(modelMaster.getPropertyFloor().getId());
            }
            PropertyFloor propertyFloor = modelMapper.map(request, PropertyFloor.class);
            Double areaSft = propertyFloor.getAreaSft();
            Double priceSft = propertyFloor.getPriceSft();
            if(modelMaster.getPropertyBasic() != null && AppConstants.PROP_TYPE_SALE.equalsIgnoreCase(modelMaster.getPropertyBasic().getRentSale())) {
                if(areaSft != null && priceSft != null) {
                    modelMaster.setTotalValue(areaSft * priceSft);
                }
            }
            if(modelMaster.getPropertyFloor() != null && CollectionUtils.isNotEmpty(modelMaster.getPropertyFloor().getAttachments())) {
                existingAttachments = modelMaster.getPropertyFloor().getAttachments();
                propertyFloor.setAttachments(existingAttachments);
            }
            modelMaster.setPropertyFloor(propertyFloor);
            modelMaster.setProgress(modelMaster.getProfilePercentage());
            //if(modelMaster.getPropertyFloor() == null) {
                modelMaster = propertyMasterRepository.save(modelMaster);
            //}
            modelMaster = uploadAttachments(modelMaster, request);
        }
        LoggerUtil.debug(logger, "OUT :: savePropertyFloorInfo", null);
        return propertyHelper.buildPropertyFloorResponse(modelMaster);
    }

    @Transactional
    public com.portal.app.payload.response.PropertyGallery saveToGallery(GalleryRequest request, Long userId, Long propId) {
        LoggerUtil.debug(logger, "IN :: saveToGallery", null);
        PropertyMaster modelMaster;
        Set<Attachment> existingAttachments;
        if(propId != null && propId > 0) {
            //update gallery details
            modelMaster = propertyMasterRepository.getOne(propId);
        } else {
            //add foor details
            modelMaster = createMasterData(userId);
        }

        ModelMapper modelMapper = new ModelMapper();
        if(modelMaster != null && modelMaster.getId() != null && modelMaster.getId() > 0) {
            LoggerUtil.debug(logger, "Updating gallery for propId : ", modelMaster.getId());
            if(modelMaster.getPropertyGallery() != null) {
                request.setId(modelMaster.getPropertyGallery().getId());
            }
            PropertyGallery propertyGallery = modelMapper.map(request, PropertyGallery.class);
            if(modelMaster.getPropertyGallery() != null && CollectionUtils.isNotEmpty(modelMaster.getPropertyGallery().getAttachments())) {
                existingAttachments = modelMaster.getPropertyGallery().getAttachments();
                propertyGallery.setAttachments(existingAttachments);
            }
            modelMaster.setPropertyGallery(propertyGallery);
            modelMaster.setProgress(modelMaster.getProfilePercentage());
            if(modelMaster.getPropertyGallery() == null) {
                modelMaster = propertyMasterRepository.save(modelMaster);
            }
            modelMaster = uploadAttachments(modelMaster, request);
        }
        LoggerUtil.debug(logger, "OUT :: saveToGallery", null);
        return propertyHelper.buildPropertyGalleryResponse(modelMaster);
    }

    @Transactional
    public com.portal.app.payload.response.PropertyAmenities saveAmenitiesInfo(AmenitiesRequest request, Long userId, Long propId) {
        LoggerUtil.debug(logger, "IN :: saveAmenitiesInfo", null);
        PropertyMaster modelMaster;

        if(propId != null && propId > 0) {
            //update property amenities details
            modelMaster = propertyMasterRepository.getOne(propId);
        } else {
            //add property amenities details
            modelMaster = createMasterData(userId);
        }

        ModelMapper modelMapper = new ModelMapper();
        if(modelMaster != null && modelMaster.getId() != null && modelMaster.getId() > 0) {
            LoggerUtil.debug(logger, "Updating property amenities details for propId : ", modelMaster.getId());
            if(modelMaster.getPropertyAmenities() != null) {
                request.setId(modelMaster.getPropertyAmenities().getId());
            }
            PropertyAmenities propertyAmenities = modelMapper.map(request, PropertyAmenities.class);
            modelMaster.setPropertyAmenities(propertyAmenities);
            modelMaster.setProgress(modelMaster.getProfilePercentage());
            modelMaster = propertyMasterRepository.save(modelMaster);
        }
        LoggerUtil.debug(logger, "OUT :: saveAmenitiesInfo", null);
        return propertyHelper.buildPropertyAmenitiesResponse(modelMaster);
    }

    @Transactional
    public com.portal.app.payload.response.PropertyMaterial savePropertyMaterialInfo(MaterialInfoRequest request, Long userId, Long propId) {
        LoggerUtil.debug(logger, "IN :: savePropertyMaterialInfo", null);
        PropertyMaster modelMaster;

        if(propId != null && propId > 0) {
            //update property basic details
            modelMaster = propertyMasterRepository.getOne(propId);
        } else {
            //add property basic details
            modelMaster = createMasterData(userId);
        }

        ModelMapper modelMapper = new ModelMapper();
        if(modelMaster != null && modelMaster.getId() != null && modelMaster.getId() > 0) {
            LoggerUtil.debug(logger, "Updating property material details for propId : ", modelMaster.getId());
            if(modelMaster.getPropertyMaterial() != null) {
                request.setId(modelMaster.getPropertyMaterial().getId());
            }
            PropertyMaterial propertyMaterial = modelMapper.map(request, PropertyMaterial.class);
            modelMaster.setPropertyMaterial(propertyMaterial);
            modelMaster.setProgress(modelMaster.getProfilePercentage());
            modelMaster = propertyMasterRepository.save(modelMaster);
        }
        LoggerUtil.debug(logger, "OUT :: savePropertyMaterialInfo", null);
        return propertyHelper.buildPropertyMaterialResponse(modelMaster);
    }

    @Transactional
    public PropertyNearby saveNearbyInfo(NearbyRequest request, Long userId, Long propId) {
        LoggerUtil.debug(logger, "IN :: saveNearbyInfo", null);
        PropertyMaster modelMaster;

        if(propId != null && propId > 0) {
            //update property amenities details
            modelMaster = propertyMasterRepository.getOne(propId);
        } else {
            //add property amenities details
            modelMaster = createMasterData(userId);
        }

        ModelMapper modelMapper = new ModelMapper();
        if(modelMaster != null && modelMaster.getId() != null && modelMaster.getId() > 0) {
            LoggerUtil.debug(logger, "Updating property nearby details for propId : ", modelMaster.getId());
            if(modelMaster.getPropertyNearBy() != null) {
                request.setId(modelMaster.getPropertyNearBy().getId());
            }
            PropertyNearBy propertyNearBy = modelMapper.map(request, PropertyNearBy.class);
            modelMaster.setPropertyNearBy(propertyNearBy);
            modelMaster.setProgress(modelMaster.getProfilePercentage());
            modelMaster = propertyMasterRepository.save(modelMaster);
        }
        LoggerUtil.debug(logger, "OUT :: saveNearbyInfo", null);
        return propertyHelper.buildPropertyNearbyResponse(modelMaster);
    }

    @Transactional
    public com.portal.app.payload.response.PropertyBanks saveBankLoanInfo(BankLoanRequest request, Long userId, Long propId) {
        LoggerUtil.debug(logger, "IN :: saveBankLoanInfo", null);
        PropertyMaster modelMaster;

        if(propId != null && propId > 0) {
            //update property amenities details
            modelMaster = propertyMasterRepository.getOne(propId);
        } else {
            //add property amenities details
            modelMaster = createMasterData(userId);
        }

        ModelMapper modelMapper = new ModelMapper();
        if(modelMaster != null && modelMaster.getId() != null && modelMaster.getId() > 0) {
            LoggerUtil.debug(logger, "Updating property banks details for propId : ", modelMaster.getId());
            if(modelMaster.getPropertyBanks() != null) {
                request.setId(modelMaster.getPropertyBanks().getId());
            }
            PropertyBanks propertyBanks = modelMapper.map(request, PropertyBanks.class);
            modelMaster.setPropertyBanks(propertyBanks);
            modelMaster.setProgress(modelMaster.getProfilePercentage());
            modelMaster = propertyMasterRepository.save(modelMaster);
        }
        LoggerUtil.debug(logger, "OUT :: saveBankLoanInfo", null);
        return propertyHelper.buildPropertyBanksResponse(modelMaster);
    }

    public boolean verifyProperty(PropertyRequest propertyRequest, int page, int size) {

        propertyRequest.setKey(AppConstants.KEY_IS_VERIFIED);
        propertyRequest.setType(AppConstants.COMMENT_TYPE_VERIFY);
        int count = propertyDAO.update(propertyRequest);

        if (count > 0) {
            return true;
        } else {
            throw new AppException("Unable to verify property. Property details not found");
        }
    }

    public boolean inactiveProperty(PropertyRequest propertyRequest, int page, int size, UserPrincipal currentUser) {

        propertyRequest.setKey(AppConstants.KEY_IS_ACTIVE);
        propertyRequest.setType(AppConstants.COMMENT_TYPE_INACTIVE);
        int count = propertyDAO.update(propertyRequest);

        if (count > 0) {
            return true;
        } else {
            throw new AppException("Unable to verify property. Property details not found");
        }
    }

    public PagedResponse<InquiryResponse> getInquiriesByUser(UserPrincipal currentUser, int page, int size) {

        CommonUtil.validatePageNumberAndSize(page, size);

        // Retrieve Properties
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Object[]> properties = propertyMasterRepository.findInquiriesByUser(currentUser.getId(), pageable);

        if (properties != null && properties.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), properties.getNumber(),
                    properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());
        }

        List<InquiryResponse> propResponseList = propertyHelper.preparePagedInquiryResponse(properties, false);

        return new PagedResponse<>(propResponseList, properties.getNumber(),
                properties.getSize(), properties.getTotalElements(), properties.getTotalPages(), properties.isLast());
    }

    public boolean generateProperties(int from, int to) {
        Random r=new Random();
        long[] users = {2, 3, 4, 5, 6, 7, 8};
        for(int i = from; i <= to; i++) {
            //long uid = users[r.nextInt(users.length)];
            long uid = 1;
            BasicInfoRequest basic = propertyHelper.prepareBasicInfoRequest(i);
            com.portal.app.payload.response.PropertyBasic propertyBasic = savePropertyBasicInfo(basic, uid, null);
            if(propertyBasic != null && propertyBasic.getpId() != null) {
                FloorRequest floor = propertyHelper.prepareFloorRequest(i);
                savePropertyFloorInfo(floor, uid, propertyBasic.getpId());
            }
        }
        return true;
    }

    private PropertyMaster uploadAttachments(PropertyMaster modelMaster, FloorRequest floorRequest) {
        Set<Attachment> attachments = new HashSet<>();
        if(ArrayUtils.isNotEmpty(floorRequest.getFiles())) {
            PropertyFloor propertyFloor = modelMaster.getPropertyFloor();
            Arrays.stream(floorRequest.getFiles()).forEach(file -> {
                Attachment attachment = new Attachment();
                try {
                    attachment.setData(file.getBytes());
                    attachment.setFileName(file.getName());
                    attachment.setFileType(file.getContentType());
                    attachment.setPropertyFloor(propertyFloor);
                    attachments.add(attachment);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            if(modelMaster.getPropertyFloor() != null && CollectionUtils.isNotEmpty(modelMaster.getPropertyFloor().getAttachments())) {
                modelMaster.getPropertyFloor().getAttachments().addAll(attachments);
            } else {
                modelMaster.getPropertyFloor().setAttachments(attachments);
            }
            modelMaster = propertyMasterRepository.save(modelMaster);
        }
        return modelMaster;
    }

    private PropertyMaster uploadAttachments(PropertyMaster modelMaster, GalleryRequest galleryRequest) {
        Set<Attachment> attachments = new HashSet<>();
        if(ArrayUtils.isNotEmpty(galleryRequest.getFiles())) {
            PropertyGallery propertyGallery = modelMaster.getPropertyGallery();
            Arrays.stream(galleryRequest.getFiles()).forEach(file -> {
                Attachment attachment = new Attachment();
                try {
                    attachment.setData(file.getBytes());
                    attachment.setFileName(file.getName());
                    attachment.setFileType(file.getContentType());
                    attachment.setPropertyGallery(propertyGallery);
                    attachments.add(attachment);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            if(modelMaster.getPropertyGallery() != null && CollectionUtils.isNotEmpty(modelMaster.getPropertyGallery().getAttachments())) {
                modelMaster.getPropertyGallery().getAttachments().addAll(attachments);
            } else {
                modelMaster.getPropertyGallery().setAttachments(attachments);
            }
            modelMaster = propertyMasterRepository.save(modelMaster);
        }
        return modelMaster;
    }

    private PropertyMaster createMasterData(Long userId) {
        //Create new property master profile.
        LoggerUtil.debug(logger, "Creating property profile with initial data", null);
        PropertyMaster modelMaster = new PropertyMaster();
        //Set profile progress
        modelMaster.setProgress(CommonUtil.roundToTwoDecimal((1.0 / 7)*100));
        //Generate property id
        modelMaster.setPropId("PROP" + CommonUtil.getRandomNumber());
        //Set isActive to default false
        modelMaster.setIsActive((byte) 0);
        //Set isVerified to default false
        modelMaster.setIsVerified((byte) 0);
        //Get user details from userId and set to master
        User user = userRepository.getOne(userId);
        modelMaster.setUser(user);
        //Save master profile
        return propertyMasterRepository.save(modelMaster);
    }


}
