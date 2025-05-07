package com.techgear.techgear_be.mappers.inventory;

import com.techgear.techgear_be.dtos.inventory.ProductInventoryLimitRequest;
import com.techgear.techgear_be.dtos.inventory.ProductInventoryLimitResponse;
import com.techgear.techgear_be.models.inventory.ProductInventoryLimit;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface ProductInventoryLimitMapper extends GenericMapper<ProductInventoryLimit, ProductInventoryLimitRequest,
        ProductInventoryLimitResponse> {

    @Override
    @Mapping(source = "productId", target = "product")
    ProductInventoryLimit requestToEntity(ProductInventoryLimitRequest request);

    @Override
    @Mapping(source = "productId", target = "product")
    ProductInventoryLimit partialUpdate(@MappingTarget ProductInventoryLimit entity, ProductInventoryLimitRequest request);

}
