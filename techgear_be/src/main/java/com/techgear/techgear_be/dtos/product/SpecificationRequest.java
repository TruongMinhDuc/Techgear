package com.techgear.techgear_be.dtos.product;

import lombok.Data;

@Data
public class SpecificationRequest {
    private String name;
    private String code;
    private String description;
    private Integer status;
}
