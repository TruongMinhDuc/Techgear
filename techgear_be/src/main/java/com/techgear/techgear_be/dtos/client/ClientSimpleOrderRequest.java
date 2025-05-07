package com.techgear.techgear_be.dtos.client;

import com.techgear.techgear_be.models.cashbook.PaymentMethodType;
import lombok.Data;

@Data
public class ClientSimpleOrderRequest {
    private PaymentMethodType paymentMethodType;
}
