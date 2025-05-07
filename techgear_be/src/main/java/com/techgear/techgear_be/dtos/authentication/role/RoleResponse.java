package com.techgear.techgear_be.dtos.authentication.role;

import lombok.Data;

import java.time.Instant;

@Data
public class RoleResponse {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String code;
    private String name;
    private Integer status;
}
