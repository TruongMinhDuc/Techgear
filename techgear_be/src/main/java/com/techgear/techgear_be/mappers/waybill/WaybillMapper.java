package com.techgear.techgear_be.mappers.waybill;

import com.techgear.techgear_be.dtos.waybill.WaybillRequest;
import com.techgear.techgear_be.dtos.waybill.WaybillResponse;
import com.techgear.techgear_be.models.waybill.Waybill;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.mappers.order.OrderMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MapperUtils.class, OrderMapper.class})
public interface WaybillMapper extends GenericMapper<Waybill, WaybillRequest, WaybillResponse> {

    @Override
    @Mapping(source = "orderId", target = "order")
    Waybill requestToEntity(WaybillRequest request);

    @Override
    @Mapping(source = "orderId", target = "order")
    Waybill partialUpdate(@MappingTarget Waybill entity, WaybillRequest request);

}
