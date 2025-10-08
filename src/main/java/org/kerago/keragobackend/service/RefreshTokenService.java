package org.kerago.keragobackend.service;

import jakarta.transaction.Transactional;
import org.kerago.keragobackend.exception.ResourceNotFoundException;
import org.kerago.keragobackend.model.RefreshToken;
import org.kerago.keragobackend.model.Users;
import org.kerago.keragobackend.repository.RefreshTokenRepository;
import org.kerago.keragobackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.cloudinary.AccessControlRule.AccessType.token;

@Service
@Transactional
public class RefreshTokenService {
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;



    public RefreshToken refreshToken(String refreshTokenString,UserDetails userDetails) {

        Users user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken existingToken = refreshTokenRepository.findByUsers(user);
         if (existingToken != null) {
            existingToken.setToken(token);
             existingToken.setExpiryDate(Instant.now().plusMillis(7 * 24 * 60 * 60 * 1000));
             return refreshTokenRepository.save(existingToken);
         }

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(refreshTokenString);
        refreshToken.setUsers(user);
        refreshToken.setExpiryDate( Instant.now().plusMillis(7 * 24 * 60 * 60 * 1000));


       return refreshTokenRepository.save(refreshToken);
    }

    public void deleteByUser(Users user) {
        refreshTokenRepository.deleteByUsers(user);
    }


    public ResponseEntity<?> logout(Authentication authentication) {
        if(authentication==null||!authentication.isAuthenticated()){
            return ResponseEntity.badRequest().body("user is not authenticated");
        }

        Users users = userRepository.findByUsername(authentication.getName())
                .orElseThrow(()->new ResourceNotFoundException("user not found"));

        refreshTokenRepository.deleteByUsers(users);

        return ResponseEntity.ok("Logout successful");
    }
}
