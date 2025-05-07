package com.techgear.techgear_be.mappers.customer;

import com.techgear.techgear_be.dtos.customer.CustomerStatusRequest;
import com.techgear.techgear_be.dtos.customer.CustomerStatusResponse;
import com.techgear.techgear_be.models.customer.CustomerStatus;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerStatusMapper extends GenericMapper<CustomerStatus, CustomerStatusRequest, CustomerStatusResponse> {
}
