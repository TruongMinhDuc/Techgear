package com.techgear.techgear_be.projections;

import com.techgear.techgear_be.models.inventory.DocketVariant;
import com.techgear.techgear_be.models.product.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductInventory {
    private Product product;
    private List<DocketVariant> transactions;
    private Integer inventory;
    private Integer waitingForDelivery;
    private Integer canBeSold;
    private Integer areComing;
}
