package com.techgear.techgear_be.mappers.general;

import com.techgear.techgear_be.dtos.general.NotificationRequest;
import com.techgear.techgear_be.dtos.general.NotificationResponse;
import com.techgear.techgear_be.models.general.Notification;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface NotificationMapper extends GenericMapper<Notification, NotificationRequest, NotificationResponse> {

    @Override
    @Mapping(source = "userId", target = "user")
    Notification requestToEntity(NotificationRequest request);

    @Override
    @Mapping(source = "userId", target = "user")
    Notification partialUpdate(@MappingTarget Notification entity, NotificationRequest request);

}
