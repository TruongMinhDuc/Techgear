package com.techgear.techgear_be.mappers.customer;

import com.techgear.techgear_be.dtos.customer.CustomerResourceRequest;
import com.techgear.techgear_be.dtos.customer.CustomerResourceResponse;
import com.techgear.techgear_be.models.customer.CustomerResource;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerResourceMapper extends GenericMapper<CustomerResource, CustomerResourceRequest, CustomerResourceResponse> {
}
