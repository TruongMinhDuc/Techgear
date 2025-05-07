package com.techgear.techgear_be.mappers.inventory;

import com.techgear.techgear_be.dtos.inventory.DocketVariantEliminatedResponse;
import com.techgear.techgear_be.dtos.inventory.DocketVariantExtendedResponse;
import com.techgear.techgear_be.dtos.inventory.DocketVariantRequest;
import com.techgear.techgear_be.dtos.inventory.DocketVariantResponse;
import com.techgear.techgear_be.models.inventory.DocketVariant;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {DocketReasonMapper.class, WarehouseMapper.class, MapperUtils.class})
public interface DocketVariantMapper extends GenericMapper<DocketVariant, DocketVariantRequest, DocketVariantResponse> {

    @Override
    @Mapping(source = "variantId", target = "variant")
    DocketVariant requestToEntity(DocketVariantRequest request);

    @Override
    @Mapping(source = "variantId", target = "variant")
    DocketVariant partialUpdate(@MappingTarget DocketVariant entity, DocketVariantRequest request);

    DocketVariantExtendedResponse docketVariantToDocketVariantExtendedResponse(DocketVariant docketVariant);

    DocketVariantEliminatedResponse docketVariantToDocketVariantEliminatedResponse(DocketVariant docketVariant);

}
