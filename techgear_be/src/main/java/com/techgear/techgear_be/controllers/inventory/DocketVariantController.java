package com.techgear.techgear_be.controllers.inventory;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.inventory.DocketVariantKeyRequest;
import com.techgear.techgear_be.models.inventory.DocketVariantKey;
import com.techgear.techgear_be.services.inventory.DocketVariantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/docket-variants")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class DocketVariantController {

    private DocketVariantService docketVariantService;

    @DeleteMapping("/{docketId}/{variantId}")
    public ResponseEntity<Void> deleteDocketVariant(@PathVariable("docketId") Long docketId,
                                                    @PathVariable("variantId") Long variantId) {
        DocketVariantKey id = new DocketVariantKey(docketId, variantId);
        docketVariantService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteDocketVariants(@RequestBody List<DocketVariantKeyRequest> idRequests) {
        List<DocketVariantKey> ids = idRequests.stream()
                .map(idRequest -> new DocketVariantKey(idRequest.getDocketId(), idRequest.getVariantId()))
                .collect(Collectors.toList());
        docketVariantService.delete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
