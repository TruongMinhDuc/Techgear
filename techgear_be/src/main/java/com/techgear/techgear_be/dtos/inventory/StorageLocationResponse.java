package com.techgear.techgear_be.dtos.inventory;

import com.techgear.techgear_be.dtos.product.VariantResponse;
import lombok.Data;

import java.time.Instant;

@Data
public class StorageLocationResponse {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private WarehouseResponse warehouse;
    private VariantResponse variant;
    private String name;
}
