package com.techgear.techgear_be.dtos.inventory;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class VariantInventoryLimitRequest {
    private Long variantId;
    @Nullable
    private Integer minimumLimit;
    @Nullable
    private Integer maximumLimit;
}
