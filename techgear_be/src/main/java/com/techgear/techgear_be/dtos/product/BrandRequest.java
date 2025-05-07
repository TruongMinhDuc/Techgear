package com.techgear.techgear_be.dtos.product;

import lombok.Data;

@Data
public class BrandRequest {
    private String name;
    private String code;
    private String description;
    private Integer status;
}
