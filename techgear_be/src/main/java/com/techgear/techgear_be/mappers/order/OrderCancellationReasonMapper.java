package com.techgear.techgear_be.mappers.order;

import com.techgear.techgear_be.dtos.order.OrderCancellationReasonRequest;
import com.techgear.techgear_be.dtos.order.OrderCancellationReasonResponse;
import com.techgear.techgear_be.models.order.OrderCancellationReason;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderCancellationReasonMapper extends GenericMapper<OrderCancellationReason, OrderCancellationReasonRequest,
        OrderCancellationReasonResponse> {}
