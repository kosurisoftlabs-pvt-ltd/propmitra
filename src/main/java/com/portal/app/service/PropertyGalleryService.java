package com.portal.app.service;

import com.portal.app.helper.PropertyHelper;
import com.portal.app.payload.response.PropertyFloor;
import com.portal.app.payload.response.PropertyGallery;
import com.portal.app.repository.PropertyMasterRepository;
import com.portal.app.util.LoggerUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyGalleryService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyGalleryService.class);

    @Autowired
    private PropertyMasterRepository propertyMasterRepository;

    @Autowired
    private PropertyHelper propertyHelper;

    public PropertyGallery getGalleryByPropId(Long propId) {
        LoggerUtil.debug(logger, "IN :: getGalleryByPropId :: propId : ", propId);
        PropertyGallery propertyGallery = null;
        Optional<com.portal.app.model.PropertyGallery> optionalGallery = propertyMasterRepository.getGalleryByPropId(propId);
        if (optionalGallery.isPresent()) {
            com.portal.app.model.PropertyGallery model = optionalGallery.get();
            propertyGallery = new ModelMapper().map(model, PropertyGallery.class);
            if (model.getPropertyMaster() != null) {
                propertyGallery.setpId(model.getPropertyMaster().getId());
                propertyGallery.setPropId(model.getPropertyMaster().getPropId());
            }
        }
        LoggerUtil.debug(logger, "OUT :: getGalleryByPropId :: response : ", propertyGallery);
        return propertyGallery;
    }


}
