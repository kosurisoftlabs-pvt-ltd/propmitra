package com.portal.app.dao;

import com.portal.app.helper.PropertyCriteriaConsumer;
import com.portal.app.helper.SearchCriteria;
import com.portal.app.model.PropertyMaster;
import com.portal.app.payload.request.PropertyRequest;
import com.portal.app.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PropertyDAO {

    private static final Logger logger = LoggerFactory.getLogger(PropertyDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int update(PropertyRequest propertyRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate update = criteriaBuilder.createCriteriaUpdate(PropertyMaster.class);
        Root propertyMaster = update.from(PropertyMaster.class);
        update.set(propertyMaster.get(propertyRequest.getKey()), Byte.valueOf(propertyRequest.getValue()));
        if(AppConstants.COMMENT_TYPE_VERIFY.equalsIgnoreCase(propertyRequest.getType())) {
            update.set(propertyMaster.get(AppConstants.VERIFY_COMMENTS), propertyRequest.getComments());
        } else if(AppConstants.COMMENT_TYPE_INACTIVE.equalsIgnoreCase(propertyRequest.getType())) {
            update.set(propertyMaster.get(AppConstants.INACTIVE_COMMENTS), propertyRequest.getComments());
        }
        Expression<String> exp = propertyMaster.get("id");
        Predicate predicate = exp.in(propertyRequest.getPropIds());
        update.where(predicate);
        Query query = entityManager.createQuery(update);
        return query.executeUpdate();
    }

    public Page<PropertyMaster> findByPostedBy(String postedBy) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PropertyMaster> query = builder.createQuery(PropertyMaster.class);
        Root propertyMaster = query.from(PropertyMaster.class);
        Predicate predicate = builder.conjunction();
        query.where(builder.like(propertyMaster.get("propertyBasic").get("postedBy"), "%" + postedBy + "%"));
        Page<PropertyMaster> result = new PageImpl<>(entityManager.createQuery(query).getResultList());
        return result;
    }
}
