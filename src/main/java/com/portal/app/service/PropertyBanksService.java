package com.portal.app.service;

import com.portal.app.helper.PropertyHelper;
import com.portal.app.payload.response.PropertyAmenities;
import com.portal.app.payload.response.PropertyBanks;
import com.portal.app.repository.PropertyMasterRepository;
import com.portal.app.util.LoggerUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyBanksService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyBanksService.class);

    @Autowired
    private PropertyMasterRepository propertyMasterRepository;

    @Autowired
    private PropertyHelper propertyHelper;

    public PropertyBanks getBanksByPropId(Long propId) {
        LoggerUtil.debug(logger, "IN :: getBanksByPropId :: propId : ", propId);
        PropertyBanks modelResponse = null;
        Optional<com.portal.app.model.PropertyBanks> optionalModel = propertyMasterRepository.findBanksByPropId(propId);
        if (optionalModel.isPresent()) {
            com.portal.app.model.PropertyBanks model = optionalModel.get();
            modelResponse = new ModelMapper().map(model, PropertyBanks.class);
            if (model.getPropertyMaster() != null) {
                modelResponse.setpId(model.getPropertyMaster().getId());
                modelResponse.setPropId(model.getPropertyMaster().getPropId());
            }
        }
        LoggerUtil.debug(logger, "OUT :: getBanksByPropId :: response : ", modelResponse);
        return modelResponse;
    }


}
