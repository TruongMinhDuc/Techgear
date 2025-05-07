package com.techgear.techgear_be.dtos.chat;

import lombok.Data;

@Data
public class RoomRequest {
    private String name;
    private Long userId;
}
