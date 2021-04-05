package com.portal.app.service;

import com.portal.app.helper.PropertyHelper;
import com.portal.app.payload.response.PropertyBasic;
import com.portal.app.payload.response.PropertyFloor;
import com.portal.app.repository.PropertyMasterRepository;
import com.portal.app.util.LoggerUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyFloorService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyFloorService.class);

    @Autowired
    private PropertyMasterRepository propertyMasterRepository;

    @Autowired
    private PropertyHelper propertyHelper;

    public PropertyFloor getFloorPlanByPropId(Long propId) {
        LoggerUtil.debug(logger, "IN :: getFloorPlanByPropId :: propId : ", propId);
        PropertyFloor propertyFloor = null;
        Optional<com.portal.app.model.PropertyFloor> optionalBasic = propertyMasterRepository.getFloorPlanByPropId(propId);
        if (optionalBasic.isPresent()) {
            com.portal.app.model.PropertyFloor model = optionalBasic.get();
            propertyFloor = new ModelMapper().map(model, PropertyFloor.class);
            if (model.getPropertyMaster() != null) {
                propertyFloor.setpId(model.getPropertyMaster().getId());
                propertyFloor.setPropId(model.getPropertyMaster().getPropId());
            }
        }
        LoggerUtil.debug(logger, "OUT :: getFloorPlanByPropId :: response : ", propertyFloor);
        return propertyFloor;
    }


}
