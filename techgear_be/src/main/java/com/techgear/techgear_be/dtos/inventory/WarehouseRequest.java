package com.techgear.techgear_be.dtos.inventory;

import com.techgear.techgear_be.dtos.address.AddressRequest;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class WarehouseRequest {
    private String code;
    private String name;
    @Nullable
    private AddressRequest address;
    private Integer status;
}
