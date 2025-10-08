package org.kerago.keragobackend.controller;

import lombok.RequiredArgsConstructor;
import org.kerago.keragobackend.dto.UserLoginRequestDto;
import org.kerago.keragobackend.model.RefreshToken;
import org.kerago.keragobackend.security.JwtUtil;
import org.kerago.keragobackend.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;


    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                            userLoginRequestDto.username(), userLoginRequestDto.password()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtUtil.generateToken(userDetails);

            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst().orElse("ROLE_USER");
            //create refresh token
            String refreshTokenString = jwtUtil.generateRefreshToken(userDetails);

            RefreshToken refreshToken = refreshTokenService.refreshToken(refreshTokenString,userDetails);


            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("refreshToken", refreshTokenString);
            response.put("role", role);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("Error", "invalid user name or password"));
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication){
        return ResponseEntity.ok(refreshTokenService.logout(authentication));
    }


}
