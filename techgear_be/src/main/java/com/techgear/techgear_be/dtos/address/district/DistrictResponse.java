package com.techgear.techgear_be.dtos.address.district;

import com.techgear.techgear_be.dtos.address.province.ProvinceResponse;
import lombok.Data;

import java.time.Instant;

@Data
public class DistrictResponse {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String name;
    private String code;
    private ProvinceResponse province;
}
