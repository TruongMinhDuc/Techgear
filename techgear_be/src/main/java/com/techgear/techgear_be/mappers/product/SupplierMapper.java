package com.techgear.techgear_be.mappers.product;

import com.techgear.techgear_be.dtos.product.SupplierRequest;
import com.techgear.techgear_be.dtos.product.SupplierResponse;
import com.techgear.techgear_be.models.product.Supplier;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.mappers.address.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AddressMapper.class)
public interface SupplierMapper extends GenericMapper<Supplier, SupplierRequest, SupplierResponse> {
}
