package com.techgear.techgear_be.controllers.client;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.authentication.user.UserResponse;
import com.techgear.techgear_be.dtos.client.ClientEmailSettingUserRequest;
import com.techgear.techgear_be.dtos.client.ClientPasswordSettingUserRequest;
import com.techgear.techgear_be.dtos.client.ClientPersonalSettingUserRequest;
import com.techgear.techgear_be.dtos.client.ClientPhoneSettingUserRequest;
import com.techgear.techgear_be.models.authentication.User;
import com.techgear.techgear_be.mappers.authentication.UserMapper;
import com.techgear.techgear_be.repositories.authentication.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client-api/users")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class ClientUserController {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getUserInfo(Authentication authentication) {
        String username = authentication.getName();
        UserResponse userResponse = userRepository.findByUsername(username)
                .map(userMapper::entityToResponse)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping("/personal")
    public ResponseEntity<UserResponse> updatePersonalSetting(Authentication authentication,
                                                              @RequestBody ClientPersonalSettingUserRequest userRequest) {
        String username = authentication.getName();
        UserResponse userResponse = userRepository.findByUsername(username)
                .map(existingUser -> userMapper.partialUpdate(existingUser, userRequest))
                .map(userRepository::save)
                .map(userMapper::entityToResponse)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping("/phone")
    public ResponseEntity<UserResponse> updatePhoneSetting(Authentication authentication,
                                                           @RequestBody ClientPhoneSettingUserRequest userRequest) {
        String username = authentication.getName();
        UserResponse userResponse = userRepository.findByUsername(username)
                .map(existingUser -> userMapper.partialUpdate(existingUser, userRequest))
                .map(userRepository::save)
                .map(userMapper::entityToResponse)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping("/email")
    public ResponseEntity<UserResponse> updateEmailSetting(Authentication authentication,
                                                           @RequestBody ClientEmailSettingUserRequest userRequest) {
        String username = authentication.getName();
        UserResponse userResponse = userRepository.findByUsername(username)
                .map(existingUser -> userMapper.partialUpdate(existingUser, userRequest))
                .map(userRepository::save)
                .map(userMapper::entityToResponse)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping("/password")
    public ResponseEntity<ObjectNode> updatePasswordSetting(Authentication authentication,
                                                            @RequestBody ClientPasswordSettingUserRequest userRequest) throws Exception {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (passwordEncoder.matches(userRequest.getOldPassword(), user.getPassword())) {
            String encodedNewPassword = passwordEncoder.encode(userRequest.getNewPassword());
            user.setPassword(encodedNewPassword);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
        } else {
            throw new Exception("Wrong old password");
        }
    }

}
