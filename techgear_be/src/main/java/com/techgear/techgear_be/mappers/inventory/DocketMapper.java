package com.techgear.techgear_be.mappers.inventory;

import com.techgear.techgear_be.dtos.inventory.DocketRequest;
import com.techgear.techgear_be.dtos.inventory.DocketResponse;
import com.techgear.techgear_be.models.inventory.Docket;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MapperUtils.class, DocketReasonMapper.class, WarehouseMapper.class, DocketVariantMapper.class})
public interface DocketMapper extends GenericMapper<Docket, DocketRequest, DocketResponse> {

    @Override
    @BeanMapping(qualifiedByName = "attachDocket")
    @Mapping(source = "reasonId", target = "reason")
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    @Mapping(source = "orderId", target = "order")
    Docket requestToEntity(DocketRequest request);

    @Override
    @BeanMapping(qualifiedByName = "attachDocket")
    @Mapping(source = "reasonId", target = "reason")
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    @Mapping(source = "orderId", target = "order")
    Docket partialUpdate(@MappingTarget Docket entity, DocketRequest request);

}
