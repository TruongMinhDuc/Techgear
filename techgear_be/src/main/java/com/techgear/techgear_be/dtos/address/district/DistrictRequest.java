package com.techgear.techgear_be.dtos.address.district;

import lombok.Data;

@Data
public class DistrictRequest {
    private String name;
    private String code;
    private Long provinceId;
}
