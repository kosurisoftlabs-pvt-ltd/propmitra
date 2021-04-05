package com.portal.app.repository;

import com.portal.app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmailOrContact(String username, String email, String contact);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByContact(String contact);

    Optional<User> findByContact(String contact);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User user set user.isActive =:isActive where user.id IN :ids")
    int approveOrReject(@Param("ids") List<Long> ids, @Param("isActive") Byte isActive);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User user set user.isActive =:isActive where user.id = :id")
    int updateStatus(@Param("id") Long id, @Param("isActive") Byte isActive);

    @Query("select u from User u  where u.isActive = -1")
    Collection<User> findRegistrations();

    @Query("select u from User u  where u.isActive = 1 or u.isActive = 0")
    Page<User> findActiveInactiveUsers(Pageable pageable);
}
