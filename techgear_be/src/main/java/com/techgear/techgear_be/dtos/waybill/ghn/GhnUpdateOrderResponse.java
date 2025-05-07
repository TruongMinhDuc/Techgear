package com.techgear.techgear_be.dtos.waybill.ghn;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class GhnUpdateOrderResponse {
    @JsonAlias("code")
    private Integer code;
    @JsonAlias("message")
    private String message;
}
