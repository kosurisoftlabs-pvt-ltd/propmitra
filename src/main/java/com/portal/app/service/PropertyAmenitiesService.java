package com.portal.app.service;

import com.portal.app.helper.PropertyHelper;
import com.portal.app.payload.response.PropertyAmenities;
import com.portal.app.repository.PropertyMasterRepository;
import com.portal.app.util.LoggerUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyAmenitiesService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyAmenitiesService.class);

    @Autowired
    private PropertyMasterRepository propertyMasterRepository;

    @Autowired
    private PropertyHelper propertyHelper;

    public PropertyAmenities getAmenitiesByPropId(Long propId) {
        LoggerUtil.debug(logger, "IN :: getAmenitiesByPropId :: propId : ", propId);
        PropertyAmenities modelResponse = null;
        Optional<com.portal.app.model.PropertyAmenities> optionalModel = propertyMasterRepository.findAmenitiesByPropId(propId);
        if (optionalModel.isPresent()) {
            com.portal.app.model.PropertyAmenities model = optionalModel.get();
            modelResponse = new ModelMapper().map(model, PropertyAmenities.class);
            if (model.getPropertyMaster() != null) {
                modelResponse.setpId(model.getPropertyMaster().getId());
                modelResponse.setPropId(model.getPropertyMaster().getPropId());
            }
        }
        LoggerUtil.debug(logger, "OUT :: getAmenitiesByPropId :: response : ", modelResponse);
        return modelResponse;
    }


}
