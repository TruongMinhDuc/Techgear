package com.techgear.techgear_be.services.chat;

import com.techgear.techgear_be.constants.ResourceNameConst;
import com.techgear.techgear_be.constants.SearchFieldsConst;
import com.techgear.techgear_be.dtos.ListResponse;
import com.techgear.techgear_be.dtos.chat.MessageRequest;
import com.techgear.techgear_be.dtos.chat.MessageResponse;
import com.techgear.techgear_be.models.chat.Message;
import com.techgear.techgear_be.mappers.chat.MessageMapper;
import com.techgear.techgear_be.repositories.authentication.UserRepository;
import com.techgear.techgear_be.repositories.chat.MessageRepository;
import com.techgear.techgear_be.repositories.chat.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private MessageMapper messageMapper;

    @Override
    public ListResponse<MessageResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return defaultFindAll(page, size, sort, filter, search, all, SearchFieldsConst.MESSAGE, messageRepository, messageMapper);
    }

    @Override
    public MessageResponse findById(Long id) {
        return defaultFindById(id, messageRepository, messageMapper, ResourceNameConst.MESSAGE);
    }

    @Override
    public MessageResponse save(MessageRequest request) {
        Message message = messageMapper.requestToEntity(request);

        userRepository.findById(request.getUserId()).ifPresent(message::setUser);

        Message messageAfterSave = messageRepository.save(message);

        roomRepository.findById(request.getRoomId())
                .ifPresent(room -> {
                    room.setUpdatedAt(Instant.now());
                    room.setLastMessage(messageAfterSave);
                    roomRepository.save(room);
                });

        return messageMapper.entityToResponse(messageAfterSave);
    }

    @Override
    public MessageResponse save(Long id, MessageRequest request) {
        return defaultSave(id, request, messageRepository, messageMapper, ResourceNameConst.MESSAGE);
    }

    @Override
    public void delete(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public void delete(List<Long> ids) {
        messageRepository.deleteAllById(ids);
    }

}
