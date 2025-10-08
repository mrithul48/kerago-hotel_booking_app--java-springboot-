package org.kerago.keragobackend.repository;

import org.apache.catalina.User;
import org.kerago.keragobackend.model.RefreshToken;
import org.kerago.keragobackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    void deleteByUsers(Users user);


    RefreshToken findByUsers(Users user);
}
