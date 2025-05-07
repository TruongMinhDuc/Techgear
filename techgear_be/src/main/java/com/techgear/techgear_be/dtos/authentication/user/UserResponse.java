package com.techgear.techgear_be.dtos.authentication.user;

import com.techgear.techgear_be.dtos.address.AddressResponse;
import com.techgear.techgear_be.dtos.authentication.role.RoleResponse;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private String gender;
    private AddressResponse address;
    private String avatar;
    private Integer status;
    private Set<RoleResponse> roles;
}
