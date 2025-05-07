package com.techgear.techgear_be.mappers.product;

import com.techgear.techgear_be.dtos.product.SpecificationRequest;
import com.techgear.techgear_be.dtos.product.SpecificationResponse;
import com.techgear.techgear_be.models.product.Specification;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecificationMapper extends GenericMapper<Specification, SpecificationRequest, SpecificationResponse> {
}
