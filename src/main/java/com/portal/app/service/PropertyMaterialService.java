package com.portal.app.service;

import com.portal.app.helper.PropertyHelper;
import com.portal.app.payload.response.PropertyAmenities;
import com.portal.app.payload.response.PropertyMaterial;
import com.portal.app.repository.PropertyMasterRepository;
import com.portal.app.util.LoggerUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyMaterialService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyMaterialService.class);

    @Autowired
    private PropertyMasterRepository propertyMasterRepository;

    @Autowired
    private PropertyHelper propertyHelper;

    public PropertyMaterial getMaterialByPropId(Long propId) {
        LoggerUtil.debug(logger, "IN :: getMaterialByPropId :: propId : ", propId);
        PropertyMaterial modelResponse = null;
        Optional<com.portal.app.model.PropertyMaterial> optionalModel = propertyMasterRepository.getMaterialByPropId(propId);
        if (optionalModel.isPresent()) {
            com.portal.app.model.PropertyMaterial model = optionalModel.get();
            modelResponse = new ModelMapper().map(model, PropertyMaterial.class);
            if (model.getPropertyMaster() != null) {
                modelResponse.setpId(model.getPropertyMaster().getId());
                modelResponse.setPropId(model.getPropertyMaster().getPropId());
            }
        }
        LoggerUtil.debug(logger, "OUT :: getMaterialByPropId :: response : ", modelResponse);
        return modelResponse;
    }


}
