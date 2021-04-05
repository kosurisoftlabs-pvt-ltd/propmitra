package com.portal.app.service;

import com.portal.app.helper.PropertyHelper;
import com.portal.app.payload.response.PropertyBasic;
import com.portal.app.repository.PropertyMasterRepository;
import com.portal.app.util.LoggerUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyBasicService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyBasicService.class);

    @Autowired
    private PropertyMasterRepository propertyMasterRepository;

    @Autowired
    private PropertyHelper propertyHelper;

    public PropertyBasic getBasicInfoByPropId(Long propId) {
        LoggerUtil.debug(logger, "IN :: getBasicInfoByPropId :: propId : ", propId);
        PropertyBasic propertyBasic = null;
        Optional<com.portal.app.model.PropertyBasic> optionalBasic = propertyMasterRepository.findBasicByPropId(propId);
        if (optionalBasic.isPresent()) {
            com.portal.app.model.PropertyBasic basicModel = optionalBasic.get();
            propertyBasic = new ModelMapper().map(basicModel, PropertyBasic.class);
            if (basicModel.getPropertyMaster() != null) {
                propertyBasic.setpId(basicModel.getPropertyMaster().getId());
                propertyBasic.setPropId(basicModel.getPropertyMaster().getPropId());
            }
        }
        LoggerUtil.debug(logger, "OUT :: getBasicInfoByPropId :: response : ", propertyBasic);
        return propertyBasic;
    }


}
