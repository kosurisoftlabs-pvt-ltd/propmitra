package com.portal.app.repository;

import com.portal.app.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyMasterRepository extends JpaRepository<PropertyMaster, Long> {

    Optional<PropertyMaster> findById(Long propId);

    Optional<PropertyMaster> findByPropId(String propId);

    @Query("select pb from PropertyBasic pb  where pb.propertyMaster.id = :id")
    Optional<PropertyBasic> findBasicByPropId(@Param("id")Long propId);

    @Query("select pf from PropertyFloor pf  where pf.propertyMaster.id = :id")
    Optional<PropertyFloor> getFloorPlanByPropId(@Param("id")Long propId);

    @Query("select am from PropertyAmenities am  where am.propertyMaster.id = :id")
    Optional<PropertyAmenities> findAmenitiesByPropId(@Param("id")Long propId);

    @Query("select pg from PropertyGallery pg  where pg.propertyMaster.id = :id")
    Optional<PropertyGallery> getGalleryByPropId(@Param("id")Long propId);

    @Query("select pm from PropertyMaterial pm  where pm.propertyMaster.id = :id")
    Optional<PropertyMaterial> getMaterialByPropId(@Param("id")Long propId);

    @Query("select nb from PropertyNearBy nb  where nb.propertyMaster.id = :id")
    Optional<PropertyNearBy> findNearbyByPropId(@Param("id")Long propId);

    @Query("select pb from PropertyBanks pb  where pb.propertyMaster.id = :id")
    Optional<PropertyBanks> findBanksByPropId(@Param("id")Long propId);

    @Query("select pm.id, pm.propId, pm.isActive, pm.isVerified, pm.totalValue, pm.createdAt, pm.user.username, " +
            "pm.propertyBasic.postedBy, pm.propertyBasic.rentSale, pm.propertyBasic.projectName, pm.propertyBasic.propLocation" +
            " , pm.propertyBasic.isGated, pm.propertyFloor.noBeds, pm.progress from PropertyMaster pm  left join PropertyFloor pf on pm.propertyFloor.id=pf.id" +
            " where pm.propertyBasic.postedBy = :postedBy")
    Page<Object[]> findByPostedBy(@Param("postedBy")String postedBy, Pageable pageable);

    @Query("select pm.id, pm.propId, pm.isActive, pm.isVerified, pm.totalValue, pm.createdAt, pm.user.username, " +
            "pm.propertyBasic.postedBy, pm.propertyBasic.rentSale, pm.propertyBasic.projectName, pm.propertyBasic.propLocation" +
            " , pm.propertyBasic.isGated, pm.propertyFloor.noBeds, pm.progress from PropertyMaster pm  where pm.propertyBasic.postedBy = :postedBy " +
            "and pm.user.username like concat('%', :username, '%')")
    Page<Object[]> findByPostedByAndName(@Param("postedBy")String postedBy, @Param("username")String username, Pageable pageable);

    @Query("select pm.id, pm.propId, pm.isActive, pm.isVerified, pm.totalValue, pm.createdAt, pm.user.username, " +
            "pb.postedBy, pb.rentSale, pb.projectName, pb.propLocation, pb.isGated, pf.noBeds, pm.progress from PropertyMaster pm" +
            " left join PropertyBasic pb on pm.propertyBasic.id=pb.id left join PropertyFloor pf on pm.propertyFloor.id=pf.id  where pm.createdBy = :uid")
    Page<Object[]> findByCreatedByUser(@Param("uid")Long uid, Pageable pageable);

    @Query("select pm.id, pm.propId, pm.isActive, pm.isVerified, pm.totalValue, pm.createdAt, pm.user.username, " +
            "pm.propertyBasic.postedBy, pm.propertyBasic.rentSale, pm.propertyBasic.projectName, pm.propertyBasic.propLocation" +
            " , pm.propertyBasic.isGated, pm.propertyFloor.noBeds, pm.progress from PropertyMaster pm  where pm.propertyBasic.postedBy = :postedBy" +
            " and pm.createdAt between :from and :to")
    Page<Object[]> findByPostedByAndDateRange(@Param("postedBy")String postedBy, @Param("from")Instant from, @Param("to")Instant to, Pageable pageable);

    @Query("select pi.id, pi.propertyMaster.id, pi.propertyMaster.propId, pi.name, pi.email, pi.contact, pi.message, " +
            "pi.createdAt from PropertyInquiry pi where pi.propertyMaster.createdBy = :uid")
    Page<Object[]> findInquiriesByUser(@Param("uid")Long uid, Pageable pageable);

    @Query("select pi.id, pi.propertyMaster.id, pi.propertyMaster.propId, pi.name, pi.email, pi.contact, pi.message, " +
            "pi.createdAt, pi.propertyMaster.user.username from PropertyInquiry pi " +
            "where pi.propertyMaster.propertyBasic.postedBy = :postedBy and pi.propertyMaster.user.username like concat('%', :username, '%')")
    Page<Object[]> findInquiriesByUserTypeAndName(@Param("postedBy")String postedBy, @Param("username")String username, Pageable pageable);

    @Query("select pi.id, pi.propertyMaster.id, pi.propertyMaster.propId, pi.name, pi.email, pi.contact, pi.message, " +
            "pi.createdAt, pi.propertyMaster.user.username from PropertyInquiry pi where pi.propertyMaster.propertyBasic.postedBy = :postedBy")
    Page<Object[]> findInquiriesByUserType(@Param("postedBy")String postedBy, Pageable pageable);

    @Query("select pi.id, pi.propertyMaster.id, pi.propertyMaster.propId, pi.name, pi.email, pi.contact, pi.message, " +
            "pi.createdAt, pi.propertyMaster.user.username from PropertyInquiry pi where pi.propertyMaster.propertyBasic.postedBy = :postedBy" +
            " and pi.createdAt between :from and :to")
    Page<Object[]> findInquiriesByDateRange(@Param("postedBy")String postedBy, @Param("from")Instant from, @Param("to")Instant to, Pageable pageable);

    Page<PropertyMaster> findByCreatedBy(Long userId, Pageable pageable);

    Page<PropertyMaster> findAll(Pageable pageable);

    Long countByCreatedBy(Long userId);

    List<PropertyMaster> findByIdIn(List<Long> propIds);

    List<PropertyMaster> findByIdIn(List<Long> propIds, Sort sort);

    List<PropertyMaster> findByUser(Long userId);

    @Query("select count(pi.id) from PropertyInquiry pi where pi.propertyMaster.id = :pId and (pi.email = :email or pi.contact = :contact)")
    int getInquiryCount(@Param("pId")Long pId, @Param("email")String email, @Param("contact")String contact);
}
