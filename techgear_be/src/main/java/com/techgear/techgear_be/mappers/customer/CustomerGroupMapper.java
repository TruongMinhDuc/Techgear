package com.techgear.techgear_be.mappers.customer;

import com.techgear.techgear_be.dtos.customer.CustomerGroupRequest;
import com.techgear.techgear_be.dtos.customer.CustomerGroupResponse;
import com.techgear.techgear_be.models.customer.CustomerGroup;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerGroupMapper extends GenericMapper<CustomerGroup, CustomerGroupRequest, CustomerGroupResponse> {
}
