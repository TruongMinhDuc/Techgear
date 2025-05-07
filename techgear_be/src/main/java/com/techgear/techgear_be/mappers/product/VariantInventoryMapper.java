package com.techgear.techgear_be.mappers.product;

import com.techgear.techgear_be.dtos.inventory.VariantInventoryResponse;
import com.techgear.techgear_be.mappers.inventory.DocketVariantMapper;
import com.techgear.techgear_be.projections.VariantInventory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = DocketVariantMapper.class)
public interface VariantInventoryMapper {

    VariantInventoryResponse toResponse(VariantInventory variantInventory);

    List<VariantInventoryResponse> toResponse(List<VariantInventory> variantInventory);

}
