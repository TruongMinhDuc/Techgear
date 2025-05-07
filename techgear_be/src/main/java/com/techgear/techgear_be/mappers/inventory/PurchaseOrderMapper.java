package com.techgear.techgear_be.mappers.inventory;

import com.techgear.techgear_be.dtos.inventory.PurchaseOrderRequest;
import com.techgear.techgear_be.dtos.inventory.PurchaseOrderResponse;
import com.techgear.techgear_be.models.inventory.PurchaseOrder;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.mappers.product.SupplierMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MapperUtils.class, SupplierMapper.class, DestinationMapper.class, PurchaseOrderVariantMapper.class})
public interface PurchaseOrderMapper extends GenericMapper<PurchaseOrder, PurchaseOrderRequest, PurchaseOrderResponse> {

    @Override
    @BeanMapping(qualifiedByName = "attachPurchaseOrder")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "destinationId", target = "destination")
    PurchaseOrder requestToEntity(PurchaseOrderRequest request);

    @Override
    @BeanMapping(qualifiedByName = "attachPurchaseOrder")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "destinationId", target = "destination")
    PurchaseOrder partialUpdate(@MappingTarget PurchaseOrder entity, PurchaseOrderRequest request);

}
