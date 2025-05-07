package com.techgear.techgear_be.mappers.product;

import com.techgear.techgear_be.dtos.product.PropertyRequest;
import com.techgear.techgear_be.dtos.product.PropertyResponse;
import com.techgear.techgear_be.models.product.Property;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PropertyMapper extends GenericMapper<Property, PropertyRequest, PropertyResponse> {
}
