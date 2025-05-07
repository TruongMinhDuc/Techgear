package com.techgear.techgear_be.dtos.client;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class ClientWishResponse {
    private Long wishId;
    private Instant wishCreatedAt;
    private ClientListedProductResponse wishProduct;
}
