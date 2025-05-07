package com.techgear.techgear_be.dtos.client;

import lombok.Data;

@Data
public class ClientPromotionResponse {
    private Long promotionId;
    private Integer promotionPercent;
}
