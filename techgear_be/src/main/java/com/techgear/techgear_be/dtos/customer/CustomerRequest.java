package com.techgear.techgear_be.dtos.customer;

import com.techgear.techgear_be.dtos.authentication.user.UserRequest;
import lombok.Data;

@Data
public class CustomerRequest {
    private UserRequest user;
    private Long customerGroupId;
    private Long customerStatusId;
    private Long customerResourceId;
}
