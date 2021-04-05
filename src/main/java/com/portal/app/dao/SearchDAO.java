package com.portal.app.dao;

import com.portal.app.helper.PropertyCriteriaConsumer;
import com.portal.app.helper.SearchCriteria;
import com.portal.app.model.PropertyMaster;
import com.portal.app.payload.request.SearchRequest;
import com.portal.app.payload.response.PropertyMetaResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class SearchDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<PropertyMaster> searchProperties(List<SearchCriteria> params, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PropertyMaster> query = builder.createQuery(PropertyMaster.class);
        Root r = query.from(PropertyMaster.class);
        Predicate predicate = builder.conjunction();
        PropertyCriteriaConsumer searchConsumer = new PropertyCriteriaConsumer(predicate, builder, r);
        params.stream().forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);
        Page<PropertyMaster> result = new PageImpl<>(entityManager.createQuery(query).getResultList());
        return result;
    }

    public Page<PropertyMaster> searchPagedProperties(SearchRequest request, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PropertyMaster> query = builder.createQuery(PropertyMaster.class);
        Root r = query.from(PropertyMaster.class);
        query.where(preparePredicates(request, builder, r));
        Page<PropertyMaster> result = new PageImpl<>(
                entityManager.createQuery(query)
                        .setFirstResult(pageable.getPageNumber())
                        .setMaxResults(pageable.getPageSize())
                        .getResultList());
        return result;
    }

    public List<PropertyMaster> searchProperties(SearchRequest request) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PropertyMaster> query = builder.createQuery(PropertyMaster.class);
        Root r = query.from(PropertyMaster.class);
        query.where(preparePredicates(request, builder, r), builder.equal(r.get("isVerified"), (byte)1), builder.equal(r.get("isActive"), (byte)1));
        List<PropertyMaster> result = entityManager.createQuery(query).getResultList();
        return result;
    }

    public PropertyMaster findByPropertyId(String propId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PropertyMaster> query = cb.createQuery(PropertyMaster.class);
        Root<PropertyMaster> root = query.from(PropertyMaster.class);
        query.where(cb.equal(root.get("propId"), propId));
        try {
            return entityManager.createQuery(query).getSingleResult();
        }  catch (Exception e) {
            throw e;
        }
    }

    public PropertyMaster findByUserAndPropId(String value, Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PropertyMaster> query = cb.createQuery(PropertyMaster.class);
        Root<PropertyMaster> root = query.from(PropertyMaster.class);
        query.where(cb.and(cb.equal(root.get("propId"), value), cb.equal(root.get("user").get("id"), id)));
        try {
            return entityManager.createQuery(query).getSingleResult();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<PropertyMaster> findPropertiesByDateRange(Instant from, Instant to) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PropertyMaster> query = cb.createQuery(PropertyMaster.class);
        Root<PropertyMaster> root = query.from(PropertyMaster.class);
        query.where(cb.between(root.get("createdAt"), from, to));
        query.orderBy(cb.desc(root.get("createdAt")));
        try {
            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            return null;
        }

    }

    public List<PropertyMaster> findPropertiesByDateRange(Instant from, Instant to, Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PropertyMaster> query = cb.createQuery(PropertyMaster.class);
        Root<PropertyMaster> root = query.from(PropertyMaster.class);
        query.where(cb.and(cb.equal(root.get("user").get("id"), userId), cb.between(root.get("createdAt"), from, to)));
        query.orderBy(cb.desc(root.get("createdAt")));
        try {
            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public PropertyMetaResponse findSearchMeta(String rentOrSale) {
        PropertyMetaResponse response = null;
        String query = "";
        if("ALL".equalsIgnoreCase(rentOrSale)) {
            query = "SELECT q.propertyFloor.noBeds, q.propertyBasic.propLocation, q.totalValue, \n" +
                    "q.propertyBasic.propType, q.propertyBasic.propStatus, q.propertyBasic.postedBy, q1.name " +
                    "FROM PropertyMaster q, City q1";
        } else {
            query = "SELECT q.propertyFloor.noBeds, q.propertyBasic.propLocation, q.totalValue, \n" +
                    "q.propertyBasic.propType, q.propertyBasic.propStatus, q.propertyBasic.postedBy, q1.name " +
                    "FROM PropertyMaster q, City q1 WHERE q.propertyBasic.rentSale='"+rentOrSale+"'";
        }
        List<Object[]> results = entityManager.createQuery(query).getResultList();
        if(CollectionUtils.isNotEmpty(results)) {
            Set<String> keywords = results.stream().map(objects -> {
                String bhk = objects[0] != null ? (String)objects[0] : StringUtils.EMPTY;
                String location = objects[1] != null ? (String)objects[1] : StringUtils.EMPTY;
                return bhk.toUpperCase() + " in " + location.toUpperCase();
            }).collect(Collectors.toSet());

            //Set<String> locations = results.stream().map(objects -> objects[1] != null ? (String)objects[1] : StringUtils.EMPTY).collect(Collectors.toCollection(TreeSet::new));
            Set<String> locations = results.stream().filter(objects -> objects[1] != null).map(objects -> (String)objects[6]).collect(Collectors.toCollection(TreeSet::new));

            //TreeSet<Double> prices = results.stream().map(objects -> objects[2] != null ? (Double)objects[2] : 0).collect(Collectors.toCollection(TreeSet::new));
            TreeSet<Double> prices = results.stream().filter(objects -> objects[2] != null).map(objects -> (Double)objects[2]).collect(Collectors.toCollection(TreeSet::new));

            //Set<String> propTypes = results.stream().map(objects -> objects[3] != null ? (String)objects[3] : StringUtils.EMPTY).collect(Collectors.toCollection(TreeSet::new));
            Set<String> propTypes = results.stream().filter(objects -> objects[3] != null).map(objects -> (String)objects[3]).collect(Collectors.toCollection(TreeSet::new));
            //Set<String> propStatuses = results.stream().map(objects -> objects[4] != null ? (String)objects[4] : StringUtils.EMPTY).collect(Collectors.toCollection(TreeSet::new));
            Set<String> propStatuses = results.stream().filter(objects -> objects[4] != null).map(objects -> (String)objects[4]).collect(Collectors.toCollection(TreeSet::new));
            //Set<String> propPostsBy = results.stream().map(objects -> objects[5] != null ? (String)objects[5] : StringUtils.EMPTY).collect(Collectors.toCollection(TreeSet::new));
            Set<String> propPostsBy = results.stream().filter(objects -> objects[5] != null).map(objects -> (String)objects[5]).collect(Collectors.toCollection(TreeSet::new));

            response = new PropertyMetaResponse();
            response.setRentSale(rentOrSale);
            response.setKeywords(keywords);
            response.setLocations(locations);
            if(CollectionUtils.isNotEmpty(prices)){
                response.setMaxPrice(prices.last());
                response.setMinPrice(prices.first());
            }
            response.setPropPostsBy(propPostsBy);
            response.setPropStatuses(propStatuses);
            response.setPropTypes(propTypes);
        }
        return response;
    }

    public Set<String> findKeywordMeta() {
        List<Object[]> results = entityManager.createNativeQuery(
                "SELECT q2.prop_floor_no_of_beds, q1.prop_basic_prop_loc FROM portal_store.prop_basic q1 JOIN portal_store.prop_floor q2 ON \n" +
                "q1.prop_basic_id=q2.prop_floor_pkid").getResultList();
        if(CollectionUtils.isNotEmpty(results)) {
            return results.stream().map(objects -> {
                String bhk = (String)objects[0];
                String location = (String)objects[1];
                 return bhk.toUpperCase() + " in " + location.toUpperCase();
            }).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    private Predicate preparePredicates(SearchRequest request, CriteriaBuilder builder, Root r) {

        //build predicate for keywords
        /*Predicate keywordPredicate = builder.disjunction();
        if (ArrayUtils.isNotEmpty(request.getKeywords())) {
            for(String keyword: request.getKeywords()) {
                builder.or(keywordPredicate, builder.like(r.get("propertyBasic").get("location"), "%" + keyword + "%"));
            }
        }*/

        //build predicate for locations
        /*Predicate locationPredicate = builder.disjunction();
        if (ArrayUtils.isNotEmpty(request.getLocations())) {
            for(String keyword: request.getLocations()) {
                builder.or(locationPredicate, builder.like(r.get("propertyBasic").get("location"), "%" + keyword + "%"));
            }
        }*/

        //build predicate for price
        /*if (ArrayUtils.isNotEmpty(request.getPrice()) && request.getPrice().length > 1) {
            Predicate pricePredicate = builder.between(r.get("totalPrice"), request.getPrice()[0], request.getPrice()[1]);
        }*/

        //build predicate for rentOrSale
        Predicate rentOrSale = builder.conjunction();
        if(StringUtils.isNotBlank(request.getRentOrSale())) {
            if (!"ALL".equalsIgnoreCase(request.getRentOrSale()))
                rentOrSale = builder.like(r.get("propertyBasic").get("rentSale"), "%" + request.getRentOrSale() + "%");
        } else {
            rentOrSale = builder.like(r.get("propertyBasic").get("rentSale"), "%Sale%");
        }

        //build predicate for location
        Predicate location = builder.conjunction();
        if(StringUtils.isNotBlank(request.getLocation())) {
            location = builder.like(r.get("propertyBasic").get("propLocation"), "%" + request.getLocation() + "%");
        }

        //build predicate for minPrice
        Predicate minPrice = builder.conjunction();
        if(StringUtils.isNotBlank(request.getMinPrice())) {
            minPrice = builder.greaterThanOrEqualTo(r.get("totalValue"), Double.valueOf(request.getMinPrice()));
        }

        //build predicate for maxPrice
        Predicate maxPrice = builder.conjunction();
        if(StringUtils.isNotBlank(request.getMaxPrice())) {
            maxPrice = builder.lessThanOrEqualTo(r.get("totalValue"), Double.valueOf(request.getMaxPrice()));
        }

        //build predicate for bhk
        Predicate bhkPredicate = builder.conjunction();
        List<Predicate> bhkPredicates = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(request.getBhk())) {
            for(String bhk: request.getBhk()) {
                bhkPredicates.add(builder.like(r.get("propertyFloor").get("noBeds"), "%" + bhk + "%"));
            }
            bhkPredicate = builder.or(bhkPredicates.toArray(new Predicate[bhkPredicates.size()]));
        }

        //build predicate for propType
        Predicate propTypePredicate = builder.conjunction();
        List<Predicate> propTypePredicates = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(request.getPropType())) {
            for(String type: request.getPropType()) {
                propTypePredicates.add(builder.like(r.get("propertyBasic").get("propType"), "%" + type + "%"));
            }
            propTypePredicate = builder.or(propTypePredicates.toArray(new Predicate[propTypePredicates.size()]));
        }

        //build predicate for propStatus
        Predicate propStatusPredicate = builder.conjunction();
        List<Predicate> propStatusPredicates = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(request.getPropStatus())) {
            for(String status: request.getPropStatus()) {
                propStatusPredicates.add(builder.like(r.get("propertyBasic").get("propStatus"), "%" + status + "%"));
            }
            propStatusPredicate = builder.or(propStatusPredicates.toArray(new Predicate[propStatusPredicates.size()]));
        }

        //build predicate for listBy
        Predicate listByPredicate = builder.conjunction();
        List<Predicate> listByPredicates = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(request.getListBy())) {
            for(String listBy: request.getListBy()) {
                listByPredicates.add(builder.like(r.get("propertyBasic").get("postedBy"), "%" + listBy + "%"));
            }
            listByPredicate = builder.or(listByPredicates.toArray(new Predicate[listByPredicates.size()]));
        }

        //build predicate for listBy
        Predicate keywordPredicate = builder.conjunction();
        List<Predicate> keywordPredicates = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(request.getKeywords())) {
            for(String keyword: request.getKeywords()) {
                if(keyword != null && keyword.indexOf(" in ") != -1) {
                    String[] parts = keyword.split(" in ");
                    Predicate bhk = builder.like(r.get("propertyFloor").get("noBeds"), "%" + parts[0].trim() + "%");
                    Predicate loc = builder.like(r.get("propertyBasic").get("propLocation"), "%" + parts[1].trim() + "%");
                    keywordPredicates.add(builder.and(bhk, loc));
                }
            }
            keywordPredicate = builder.or(keywordPredicates.toArray(new Predicate[keywordPredicates.size()]));
        }

        return builder.and(rentOrSale, location, minPrice, maxPrice, bhkPredicate, propTypePredicate, propStatusPredicate, listByPredicate, keywordPredicate);
    }
}
