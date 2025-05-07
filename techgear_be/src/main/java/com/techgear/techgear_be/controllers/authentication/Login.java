package com.techgear.techgear_be.controllers.authentication;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.authentication.JwtResponse;
import com.techgear.techgear_be.dtos.authentication.login.LoginRequest;
import com.techgear.techgear_be.services.authetication.RefreshTokenService;
import com.techgear.techgear_be.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/auth/login")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class Login {

    private AuthenticationManager authenticationManager;
    private RefreshTokenService refreshTokenService;
    private JwtUtils jwtUtils;
    @PostMapping("")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshToken = refreshTokenService.createRefreshToken(authentication).getToken();

        return ResponseEntity.ok(new JwtResponse("Login success!", jwt, refreshToken, Instant.now()));
    }
}
