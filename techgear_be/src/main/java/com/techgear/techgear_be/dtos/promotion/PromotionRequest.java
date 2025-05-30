package com.techgear.techgear_be.dtos.promotion;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.techgear.techgear_be.utils.DefaultInstantDeserializer;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class PromotionRequest {
    private String name;
    @JsonDeserialize(using = DefaultInstantDeserializer.class)
    private Instant startDate;
    @JsonDeserialize(using = DefaultInstantDeserializer.class)
    private Instant endDate;
    private Integer percent;
    private Integer status;
    private Set<Long> productIds;
    private Set<Long> categoryIds;
}
