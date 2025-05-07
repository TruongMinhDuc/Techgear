package com.techgear.techgear_be.mappers.authentication;

import com.techgear.techgear_be.dtos.authentication.role.RoleRequest;
import com.techgear.techgear_be.dtos.authentication.role.RoleResponse;
import com.techgear.techgear_be.models.authentication.Role;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends GenericMapper<Role, RoleRequest, RoleResponse> {
}
