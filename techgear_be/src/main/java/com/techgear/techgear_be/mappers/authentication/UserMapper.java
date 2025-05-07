package com.techgear.techgear_be.mappers.authentication;

import com.techgear.techgear_be.dtos.authentication.user.UserRequest;
import com.techgear.techgear_be.dtos.authentication.user.UserResponse;
import com.techgear.techgear_be.dtos.client.ClientEmailSettingUserRequest;
import com.techgear.techgear_be.dtos.client.ClientPersonalSettingUserRequest;
import com.techgear.techgear_be.dtos.client.ClientPhoneSettingUserRequest;
import com.techgear.techgear_be.models.authentication.User;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.mappers.address.AddressMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MapperUtils.class, AddressMapper.class})
public interface UserMapper extends GenericMapper<User, UserRequest, UserResponse> {

    @Override
    @BeanMapping(qualifiedByName = "attachUser")
    @Mapping(source = "password", target = "password", qualifiedByName = "hashPassword")
    User requestToEntity(UserRequest request);

    @Override
    @BeanMapping(qualifiedByName = "attachUser")
    @Mapping(source = "password", target = "password", qualifiedByName = "hashPassword",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(@MappingTarget User entity, UserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(@MappingTarget User entity, ClientPersonalSettingUserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(@MappingTarget User entity, ClientPhoneSettingUserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(@MappingTarget User entity, ClientEmailSettingUserRequest request);

}
