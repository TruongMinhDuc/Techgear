package com.techgear.techgear_be.services.inventory;

import com.techgear.techgear_be.dtos.order.OrderVariantRequest;
import com.techgear.techgear_be.dtos.order.OrderVariantResponse;
import com.techgear.techgear_be.models.order.OrderVariantKey;
import com.techgear.techgear_be.services.CrudService;

public interface OrderVariantService extends CrudService<OrderVariantKey, OrderVariantRequest, OrderVariantResponse> {}
