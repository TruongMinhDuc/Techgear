package com.techgear.techgear_be.dtos.promotion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromotionCheckingResponse {
    private boolean promotionable;
}
