package com.techgear.techgear_be.dtos.client;

import com.techgear.techgear_be.dtos.address.AddressRequest;
import lombok.Data;

@Data
public class ClientPersonalSettingUserRequest {
    private String username;
    private String fullname;
    private String gender;
    private AddressRequest address;
}
