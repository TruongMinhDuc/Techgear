package com.techgear.techgear_be.mappers.product;

import com.techgear.techgear_be.dtos.product.UnitRequest;
import com.techgear.techgear_be.dtos.product.UnitResponse;
import com.techgear.techgear_be.models.product.Unit;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnitMapper extends GenericMapper<Unit, UnitRequest, UnitResponse> {
}
