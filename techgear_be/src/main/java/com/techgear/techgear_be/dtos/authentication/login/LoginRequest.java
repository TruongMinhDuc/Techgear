package com.techgear.techgear_be.dtos.authentication.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
