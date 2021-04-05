package com.portal.app.repository;

import com.portal.app.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {

    Optional<VerificationToken> findByUserEmail(String email);

    Optional<VerificationToken> findByContact(String contact);

    Optional<VerificationToken> findByToken(String token);
}
