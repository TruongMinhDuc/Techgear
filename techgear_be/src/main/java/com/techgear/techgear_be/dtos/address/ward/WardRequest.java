package com.techgear.techgear_be.dtos.address.ward;

import lombok.Data;

@Data
public class WardRequest {
    private String name;
    private String code;
    private Long districtId;
}
