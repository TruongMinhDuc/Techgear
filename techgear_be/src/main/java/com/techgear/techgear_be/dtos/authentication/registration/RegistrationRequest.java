package com.techgear.techgear_be.dtos.authentication.registration;

import lombok.Data;

@Data
public class RegistrationRequest {
    private Long userId;
    private String token;
}
