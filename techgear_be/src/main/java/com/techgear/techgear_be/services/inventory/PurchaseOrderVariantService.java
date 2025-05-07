package com.techgear.techgear_be.services.inventory;

import com.techgear.techgear_be.dtos.inventory.PurchaseOrderVariantRequest;
import com.techgear.techgear_be.dtos.inventory.PurchaseOrderVariantResponse;
import com.techgear.techgear_be.models.inventory.PurchaseOrderVariantKey;
import com.techgear.techgear_be.services.CrudService;

public interface PurchaseOrderVariantService extends CrudService<PurchaseOrderVariantKey, PurchaseOrderVariantRequest,
        PurchaseOrderVariantResponse> {}
