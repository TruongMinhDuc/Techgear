package com.techgear.techgear_be.dtos.general;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventInitiationResponse {
    private String eventSourceUuid;
}
