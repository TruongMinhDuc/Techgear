package com.techgear.techgear_be.mappers.order;


import com.techgear.techgear_be.dtos.order.OrderResourceRequest;
import com.techgear.techgear_be.dtos.order.OrderResourceResponse;
import com.techgear.techgear_be.models.order.OrderResource;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.mappers.customer.CustomerResourceMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MapperUtils.class, CustomerResourceMapper.class})
public interface OrderResourceMapper extends GenericMapper<OrderResource, OrderResourceRequest, OrderResourceResponse> {

    @Override
    @Mapping(source = "customerResourceId", target = "customerResource")
    OrderResource requestToEntity(OrderResourceRequest request);

    @Override
    @Mapping(source = "customerResourceId", target = "customerResource")
    OrderResource partialUpdate(@MappingTarget OrderResource entity, OrderResourceRequest request);

}
