package com.techgear.techgear_be.mappers.employee;

import com.techgear.techgear_be.dtos.employee.OfficeRequest;
import com.techgear.techgear_be.dtos.employee.OfficeResponse;
import com.techgear.techgear_be.models.employee.Office;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.mappers.address.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AddressMapper.class)
public interface OfficeMapper extends GenericMapper<Office, OfficeRequest, OfficeResponse> {
}
