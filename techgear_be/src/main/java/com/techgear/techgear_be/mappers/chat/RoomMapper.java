package com.techgear.techgear_be.mappers.chat;

import com.techgear.techgear_be.dtos.chat.RoomRequest;
import com.techgear.techgear_be.dtos.chat.RoomResponse;
import com.techgear.techgear_be.models.chat.Room;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface RoomMapper extends GenericMapper<Room, RoomRequest, RoomResponse> {

    @Override
    @Mapping(source = "userId", target = "user")
    Room requestToEntity(RoomRequest request);

}
