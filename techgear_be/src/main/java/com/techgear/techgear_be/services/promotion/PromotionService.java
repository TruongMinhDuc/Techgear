package com.techgear.techgear_be.services.promotion;

import com.techgear.techgear_be.dtos.promotion.PromotionRequest;
import com.techgear.techgear_be.dtos.promotion.PromotionResponse;
import com.techgear.techgear_be.services.CrudService;

import java.time.Instant;

public interface PromotionService extends CrudService<Long, PromotionRequest, PromotionResponse> {

    boolean checkCanCreatePromotionForProduct(Long productId, Instant startDate, Instant endDate);

}
