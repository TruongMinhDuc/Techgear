package com.techgear.techgear_be.dtos.chat;

import lombok.Data;

import java.util.List;

@Data
public class ClientRoomExistenceResponse {
    private boolean roomExistence;
    private RoomResponse roomResponse;
    private List<MessageResponse> roomRecentMessages;
}
