package com.techgear.techgear_be.dtos.employee;

import com.techgear.techgear_be.dtos.address.AddressResponse;
import lombok.Data;

import java.time.Instant;

@Data
public class OfficeResponse {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String name;
    private AddressResponse address;
    private Integer status;
}
