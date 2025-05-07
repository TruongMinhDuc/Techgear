package com.techgear.techgear_be.dtos.inventory;

import lombok.Data;

@Data
public class DocketVariantKeyRequest {
    private Long docketId;
    private Long variantId;
}
