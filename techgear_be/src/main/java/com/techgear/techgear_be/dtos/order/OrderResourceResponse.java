package com.techgear.techgear_be.dtos.order;

import com.techgear.techgear_be.dtos.customer.CustomerResourceResponse;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Data
public class OrderResourceResponse {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String code;
    private String name;
    private String color;
    @Nullable
    private CustomerResourceResponse customerResource;
    private Integer status;
}
