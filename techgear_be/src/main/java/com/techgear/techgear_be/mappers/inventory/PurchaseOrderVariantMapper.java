package com.techgear.techgear_be.mappers.inventory;

import com.techgear.techgear_be.dtos.inventory.PurchaseOrderVariantRequest;
import com.techgear.techgear_be.dtos.inventory.PurchaseOrderVariantResponse;
import com.techgear.techgear_be.models.inventory.PurchaseOrderVariant;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface PurchaseOrderVariantMapper extends GenericMapper<PurchaseOrderVariant, PurchaseOrderVariantRequest,
        PurchaseOrderVariantResponse> {

    @Override
    @Mapping(source = "variantId", target = "variant")
    PurchaseOrderVariant requestToEntity(PurchaseOrderVariantRequest request);

    @Override
    @Mapping(source = "variantId", target = "variant")
    PurchaseOrderVariant partialUpdate(@MappingTarget PurchaseOrderVariant entity, PurchaseOrderVariantRequest request);

}
