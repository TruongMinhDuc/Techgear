package com.techgear.techgear_be.mappers.product;

import com.techgear.techgear_be.dtos.product.BrandRequest;
import com.techgear.techgear_be.dtos.product.BrandResponse;
import com.techgear.techgear_be.models.product.Brand;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandMapper extends GenericMapper<Brand, BrandRequest, BrandResponse> {}
