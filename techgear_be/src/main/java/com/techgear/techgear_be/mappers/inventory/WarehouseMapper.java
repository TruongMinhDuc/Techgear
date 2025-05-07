package com.techgear.techgear_be.mappers.inventory;

import com.techgear.techgear_be.dtos.inventory.WarehouseRequest;
import com.techgear.techgear_be.dtos.inventory.WarehouseResponse;
import com.techgear.techgear_be.models.inventory.Warehouse;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.mappers.address.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AddressMapper.class)
public interface WarehouseMapper extends GenericMapper<Warehouse, WarehouseRequest, WarehouseResponse> {}
