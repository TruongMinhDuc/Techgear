package com.techgear.techgear_be.mappers.product;

import com.techgear.techgear_be.dtos.product.VariantRequest;
import com.techgear.techgear_be.dtos.product.VariantResponse;
import com.techgear.techgear_be.models.product.Variant;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VariantMapper extends GenericMapper<Variant, VariantRequest, VariantResponse> {
}
