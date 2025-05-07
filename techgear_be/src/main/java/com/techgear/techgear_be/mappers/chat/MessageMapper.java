package com.techgear.techgear_be.mappers.chat;

import com.techgear.techgear_be.dtos.chat.MessageRequest;
import com.techgear.techgear_be.dtos.chat.MessageResponse;
import com.techgear.techgear_be.models.chat.Message;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface MessageMapper extends GenericMapper<Message, MessageRequest, MessageResponse> {

    @Override
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "roomId", target = "room")
    Message requestToEntity(MessageRequest request);

}
