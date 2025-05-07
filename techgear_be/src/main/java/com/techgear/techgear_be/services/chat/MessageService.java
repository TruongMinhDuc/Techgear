package com.techgear.techgear_be.services.chat;

import com.techgear.techgear_be.dtos.chat.MessageRequest;
import com.techgear.techgear_be.dtos.chat.MessageResponse;
import com.techgear.techgear_be.services.CrudService;

public interface MessageService extends CrudService<Long, MessageRequest, MessageResponse> {}
