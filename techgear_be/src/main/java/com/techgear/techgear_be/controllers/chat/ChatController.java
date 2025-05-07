package com.techgear.techgear_be.controllers.chat;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.ListResponse;
import com.techgear.techgear_be.dtos.chat.MessageRequest;
import com.techgear.techgear_be.dtos.chat.MessageResponse;
import com.techgear.techgear_be.services.chat.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class ChatController {

    private SimpMessagingTemplate simpMessagingTemplate;
    private MessageService messageService;

    @MessageMapping("/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, @Payload MessageRequest message) {
        MessageResponse messageResponse = messageService.save(message);
        simpMessagingTemplate.convertAndSend("/chat/receive/" + roomId, messageResponse);
    }



}
