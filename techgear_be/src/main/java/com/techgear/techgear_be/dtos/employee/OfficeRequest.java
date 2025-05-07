package com.techgear.techgear_be.dtos.employee;

import com.techgear.techgear_be.dtos.address.AddressRequest;
import lombok.Data;

@Data
public class OfficeRequest {
    private String name;
    private AddressRequest address;
    private Integer status;
}
