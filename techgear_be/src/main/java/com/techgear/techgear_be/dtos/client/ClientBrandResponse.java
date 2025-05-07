package com.techgear.techgear_be.dtos.client;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ClientBrandResponse {
    private Long brandId;
    private String brandName;
}
