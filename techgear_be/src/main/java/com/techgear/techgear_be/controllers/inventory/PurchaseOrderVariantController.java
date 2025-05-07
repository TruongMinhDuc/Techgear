package com.techgear.techgear_be.controllers.inventory;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.inventory.PurchaseOrderVariantKeyRequest;
import com.techgear.techgear_be.models.inventory.PurchaseOrderVariantKey;
import com.techgear.techgear_be.services.inventory.PurchaseOrderVariantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/purchase-order-variants")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class PurchaseOrderVariantController {

    private PurchaseOrderVariantService purchaseOrderVariantService;

    @DeleteMapping("/{purchaseOrderId}/{variantId}")
    public ResponseEntity<Void> deletePurchaseOrderVariant(@PathVariable("purchaseOrderId") Long purchaseOrderId,
                                                           @PathVariable("variantId") Long variantId) {
        PurchaseOrderVariantKey id = new PurchaseOrderVariantKey(purchaseOrderId, variantId);
        purchaseOrderVariantService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePurchaseOrderVariants(@RequestBody List<PurchaseOrderVariantKeyRequest> idRequests) {
        List<PurchaseOrderVariantKey> ids = idRequests.stream()
                .map(idRequest -> new PurchaseOrderVariantKey(idRequest.getPurchaseOrderId(), idRequest.getVariantId()))
                .collect(Collectors.toList());
        purchaseOrderVariantService.delete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
