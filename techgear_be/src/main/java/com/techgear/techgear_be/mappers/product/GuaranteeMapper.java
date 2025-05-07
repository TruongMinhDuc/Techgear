package com.techgear.techgear_be.mappers.product;

import com.techgear.techgear_be.dtos.product.GuaranteeRequest;
import com.techgear.techgear_be.dtos.product.GuaranteeResponse;
import com.techgear.techgear_be.models.product.Guarantee;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GuaranteeMapper extends GenericMapper<Guarantee, GuaranteeRequest, GuaranteeResponse> {
}
