package com.techgear.techgear_be.controllers.promotion;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.promotion.PromotionCheckingResponse;
import com.techgear.techgear_be.services.promotion.PromotionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/promotions")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class PromotionController {

    private PromotionService promotionService;

    @GetMapping("/checking")
    public ResponseEntity<PromotionCheckingResponse> checkCanCreatePromotionForProduct(
            @RequestParam Long productId,
            @RequestParam Instant startDate,
            @RequestParam Instant endDate
    ) {
        boolean promotionable = promotionService.checkCanCreatePromotionForProduct(productId, startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(new PromotionCheckingResponse(promotionable));
    }

}
