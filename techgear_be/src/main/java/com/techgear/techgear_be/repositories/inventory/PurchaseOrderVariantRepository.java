package com.techgear.techgear_be.repositories.inventory;

import com.techgear.techgear_be.models.inventory.PurchaseOrderVariant;
import com.techgear.techgear_be.models.inventory.PurchaseOrderVariantKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PurchaseOrderVariantRepository extends JpaRepository<PurchaseOrderVariant, PurchaseOrderVariantKey>,
        JpaSpecificationExecutor<PurchaseOrderVariant> {}
