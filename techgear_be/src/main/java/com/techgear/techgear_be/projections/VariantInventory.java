package com.techgear.techgear_be.projections;

import com.techgear.techgear_be.models.inventory.DocketVariant;
import com.techgear.techgear_be.models.product.Variant;
import lombok.Data;

import java.util.List;

@Data
public class VariantInventory {
    private Variant variant;
    private List<DocketVariant> transactions;
    private Integer inventory;
    private Integer waitingForDelivery;
    private Integer canBeSold;
    private Integer areComing;
}
