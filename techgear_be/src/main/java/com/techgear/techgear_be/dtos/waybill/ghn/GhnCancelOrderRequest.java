package com.techgear.techgear_be.dtos.waybill.ghn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GhnCancelOrderRequest {
    @JsonProperty("order_codes")
    private List<String> orderCodes;
}
