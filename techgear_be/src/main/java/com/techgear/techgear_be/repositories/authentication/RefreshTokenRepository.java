package com.techgear.techgear_be.repositories.authentication;

import com.techgear.techgear_be.models.authentication.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>, JpaSpecificationExecutor<RefreshToken> {

    Optional<RefreshToken> findByToken(String token);

}
