package com.techgear.techgear_be.controllers.authentication;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.authentication.JwtResponse;
import com.techgear.techgear_be.dtos.authentication.RefreshTokenRequest;
import com.techgear.techgear_be.dtos.authentication.ResetPasswordRequest;
import com.techgear.techgear_be.dtos.authentication.user.UserResponse;
import com.techgear.techgear_be.models.authentication.RefreshToken;
import com.techgear.techgear_be.models.authentication.User;
import com.techgear.techgear_be.exceptions.RefreshTokenException;
import com.techgear.techgear_be.mappers.authentication.UserMapper;
import com.techgear.techgear_be.repositories.authentication.UserRepository;
import com.techgear.techgear_be.services.auth.VerificationService;
import com.techgear.techgear_be.services.authetication.RefreshTokenService;
import com.techgear.techgear_be.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class AuthController {

    private AuthenticationManager authenticationManager;
    private VerificationService verificationService;
    private RefreshTokenService refreshTokenService;
    private JwtUtils jwtUtils;
    private UserRepository userRepository;
    private UserMapper userMapper;

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        String jwt = refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(User::getUsername)
                .map(jwtUtils::generateTokenFromUsername)
                .orElseThrow(() -> new RefreshTokenException("Refresh token was expired. Please make a new signin request!"));

        return ResponseEntity.ok(new JwtResponse("Refresh token", jwt, refreshToken, Instant.now()));
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<ObjectNode> forgotPassword(@RequestParam String email) {
        verificationService.forgetPassword(email);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ObjectNode> resetPassword(@RequestBody ResetPasswordRequest resetPassword) {
        verificationService.resetPassword(resetPassword);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getAdminUserInfo(Authentication authentication) {
        String username = authentication.getName();
        UserResponse userResponse = userRepository.findByUsername(username)
                .map(userMapper::entityToResponse)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

}
