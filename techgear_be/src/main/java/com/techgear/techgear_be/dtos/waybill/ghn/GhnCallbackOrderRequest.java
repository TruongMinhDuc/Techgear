package com.techgear.techgear_be.dtos.waybill.ghn;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GhnCallbackOrderRequest {
    private BigDecimal codAmount;
    private String orderCode;
    private String description;
    private String reason;
    private String reasonCode;
    private Integer shopID;
    private Integer width;
    private Integer weight;
    private String status;
}
