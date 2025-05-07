package com.techgear.techgear_be.dtos.waybill.ghn;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techgear.techgear_be.models.waybill.RequiredNote;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class GhnUpdateOrderRequest {
    @JsonProperty("order_code")
    private String orderCode;
    @JsonProperty("note")
    @Nullable
    private String note;
    @JsonProperty("required_note")
    private RequiredNote requiredNote;
}
