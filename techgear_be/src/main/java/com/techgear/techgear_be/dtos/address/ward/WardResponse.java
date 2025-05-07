package com.techgear.techgear_be.dtos.address.ward;

import com.techgear.techgear_be.dtos.address.district.DistrictResponse;
import lombok.Data;

import java.time.Instant;

@Data
public class WardResponse {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String name;
    private String code;
    private DistrictResponse district;
}
