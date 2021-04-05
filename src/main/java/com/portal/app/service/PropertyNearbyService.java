package com.portal.app.service;

import com.portal.app.helper.PropertyHelper;
import com.portal.app.payload.response.PropertyAmenities;
import com.portal.app.payload.response.PropertyNearby;
import com.portal.app.repository.PropertyMasterRepository;
import com.portal.app.util.LoggerUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyNearbyService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyNearbyService.class);

    @Autowired
    private PropertyMasterRepository propertyMasterRepository;

    @Autowired
    private PropertyHelper propertyHelper;

    public PropertyNearby getNearbyByPropId(Long propId) {
        LoggerUtil.debug(logger, "IN :: getNearbyByPropId :: propId : ", propId);
        PropertyNearby response = null;
        Optional<com.portal.app.model.PropertyNearBy> optionalModel = propertyMasterRepository.findNearbyByPropId(propId);
        if (optionalModel.isPresent()) {
            com.portal.app.model.PropertyNearBy model = optionalModel.get();
            response = new ModelMapper().map(model, PropertyNearby.class);
            if (model.getPropertyMaster() != null) {
                response.setpId(model.getPropertyMaster().getId());
                response.setPropId(model.getPropertyMaster().getPropId());
            }
        }
        LoggerUtil.debug(logger, "OUT :: getNearbyByPropId :: response : ", response);
        return response;
    }


}
