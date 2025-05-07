package com.techgear.techgear_be.dtos.inventory;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class TransferRequest {
    private String code;
    private DocketRequest exportDocket;
    private DocketRequest importDocket;
    @Nullable
    private String note;
}
