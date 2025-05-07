package com.techgear.techgear_be.dtos.inventory;

import lombok.Data;

@Data
public class CountVariantKeyRequest {
    private Long countId;
    private Long variantId;
}
