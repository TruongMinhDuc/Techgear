package com.techgear.techgear_be.mappers.inventory;

import com.techgear.techgear_be.dtos.inventory.StorageLocationRequest;
import com.techgear.techgear_be.dtos.inventory.StorageLocationResponse;
import com.techgear.techgear_be.models.inventory.StorageLocation;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.mappers.product.VariantMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MapperUtils.class, WarehouseMapper.class, VariantMapper.class})
public interface StorageLocationMapper extends GenericMapper<StorageLocation, StorageLocationRequest, StorageLocationResponse> {

    @Override
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "variantId", target = "variant")
    StorageLocation requestToEntity(StorageLocationRequest request);

    @Override
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "variantId", target = "variant")
    StorageLocation partialUpdate(@MappingTarget StorageLocation entity, StorageLocationRequest request);

}
