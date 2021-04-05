package com.portal.app.repository;

import com.portal.app.model.PropertyMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface StatusRepository extends JpaRepository<PropertyMaster, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update PropertyMaster pm set pm.isActive =:isActive where pm.id = :id")
    int updateInquiryStatus(@Param("id") Long id, @Param("isActive") Byte isActive);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update PropertyMaster pm set pm.isVerified =:isVerify where pm.id = :id")
    int updateVerifyStatus(@Param("id") Long id, @Param("isVerify") Byte isVerify);

}
