package com.techgear.techgear_be.dtos.authentication.role;

import lombok.Data;

@Data
public class RoleRequest {
    private String code;
    private String name;
    private Integer status;
}
