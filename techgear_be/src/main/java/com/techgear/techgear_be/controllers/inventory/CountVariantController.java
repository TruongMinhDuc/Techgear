package com.techgear.techgear_be.controllers.inventory;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.inventory.CountVariantKeyRequest;
import com.techgear.techgear_be.models.inventory.CountVariantKey;
import com.techgear.techgear_be.services.inventory.CountVariantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/count-variants")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class CountVariantController {

    private CountVariantService countVariantService;

    @DeleteMapping("/{countId}/{variantId}")
    public ResponseEntity<Void> deleteCountVariant(@PathVariable("countId") Long countId,
                                                   @PathVariable("variantId") Long variantId) {
        CountVariantKey id = new CountVariantKey(countId, variantId);
        countVariantService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCountVariants(@RequestBody List<CountVariantKeyRequest> idRequests) {
        List<CountVariantKey> ids = idRequests.stream()
                .map(idRequest -> new CountVariantKey(idRequest.getCountId(), idRequest.getVariantId()))
                .collect(Collectors.toList());
        countVariantService.delete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
