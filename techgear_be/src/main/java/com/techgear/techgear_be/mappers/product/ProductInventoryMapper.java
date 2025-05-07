package com.techgear.techgear_be.mappers.product;

import com.techgear.techgear_be.dtos.inventory.ProductInventoryResponse;
import com.techgear.techgear_be.mappers.inventory.DocketVariantMapper;
import com.techgear.techgear_be.projections.ProductInventory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {DocketVariantMapper.class, BrandMapper.class, SupplierMapper.class})
public interface ProductInventoryMapper {

    ProductInventoryResponse toResponse(ProductInventory productInventory);

    List<ProductInventoryResponse> toResponse(List<ProductInventory> productInventory);

}
