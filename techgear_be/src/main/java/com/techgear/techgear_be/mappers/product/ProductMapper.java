package com.techgear.techgear_be.mappers.product;

import com.techgear.techgear_be.dtos.product.ProductRequest;
import com.techgear.techgear_be.dtos.product.ProductResponse;
import com.techgear.techgear_be.models.product.Product;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.mappers.general.ImageMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MapperUtils.class, ImageMapper.class, BrandMapper.class, SupplierMapper.class, UnitMapper.class,
                GuaranteeMapper.class})
public interface ProductMapper extends GenericMapper<Product, ProductRequest, ProductResponse> {

    @Override
    @BeanMapping(qualifiedByName = "attachProduct")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "brandId", target = "brand")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "unitId", target = "unit")
    @Mapping(source = "guaranteeId", target = "guarantee")
    Product requestToEntity(ProductRequest request);

    @Override
    @BeanMapping(qualifiedByName = "attachProduct")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "brandId", target = "brand")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "unitId", target = "unit")
    @Mapping(source = "guaranteeId", target = "guarantee")
    Product partialUpdate(@MappingTarget Product entity, ProductRequest request);

}
