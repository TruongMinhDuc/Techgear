package com.techgear.techgear_be.services.order;

import com.techgear.techgear_be.dtos.client.ClientConfirmedOrderResponse;
import com.techgear.techgear_be.dtos.client.ClientSimpleOrderRequest;

public interface OrderService {

    void cancelOrder(String code);

    ClientConfirmedOrderResponse createClientOrder(ClientSimpleOrderRequest request);

    void captureTransactionPaypal(String paypalOrderId, String payerId);

}
