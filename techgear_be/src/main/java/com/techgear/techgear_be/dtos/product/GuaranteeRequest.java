package com.techgear.techgear_be.dtos.product;

import lombok.Data;

@Data
public class GuaranteeRequest {
    private String name;
    private String description;
    private Integer status;
}
