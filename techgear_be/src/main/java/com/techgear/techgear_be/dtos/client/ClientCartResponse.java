package com.techgear.techgear_be.dtos.client;

import lombok.Data;

import java.util.Set;

@Data
public class ClientCartResponse {
    private Long cartId;
    private Set<ClientCartVariantResponse> cartItems;
}
