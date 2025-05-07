package com.techgear.techgear_be.mappers.inventory;

import com.techgear.techgear_be.dtos.inventory.CountRequest;
import com.techgear.techgear_be.dtos.inventory.CountResponse;
import com.techgear.techgear_be.models.inventory.Count;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MapperUtils.class, WarehouseMapper.class, CountVariantMapper.class})
public interface CountMapper extends GenericMapper<Count, CountRequest, CountResponse> {

    @Override
    @BeanMapping(qualifiedByName = "attachCount")
    @Mapping(source = "warehouseId", target = "warehouse")
    Count requestToEntity(CountRequest request);

    @Override
    @BeanMapping(qualifiedByName = "attachCount")
    @Mapping(source = "warehouseId", target = "warehouse")
    Count partialUpdate(@MappingTarget Count entity, CountRequest request);

}
