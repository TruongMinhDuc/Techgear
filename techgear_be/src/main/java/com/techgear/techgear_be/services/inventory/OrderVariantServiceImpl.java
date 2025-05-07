package com.techgear.techgear_be.services.inventory;

import com.techgear.techgear_be.constants.ResourceNameConst;
import com.techgear.techgear_be.constants.SearchFieldsConst;
import com.techgear.techgear_be.dtos.ListResponse;
import com.techgear.techgear_be.dtos.order.OrderVariantRequest;
import com.techgear.techgear_be.dtos.order.OrderVariantResponse;
import com.techgear.techgear_be.models.order.OrderVariantKey;
import com.techgear.techgear_be.mappers.order.OrderVariantMapper;
import com.techgear.techgear_be.repositories.order.OrderVariantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderVariantServiceImpl implements OrderVariantService {

    private OrderVariantRepository orderVariantRepository;

    private OrderVariantMapper orderVariantMapper;

    @Override
    public ListResponse<OrderVariantResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return defaultFindAll(page, size, sort, filter, search, all, SearchFieldsConst.ORDER_VARIANT, orderVariantRepository, orderVariantMapper);
    }

    @Override
    public OrderVariantResponse findById(OrderVariantKey id) {
        return defaultFindById(id, orderVariantRepository, orderVariantMapper, ResourceNameConst.ORDER_VARIANT);
    }

    @Override
    public OrderVariantResponse save(OrderVariantRequest request) {
        return defaultSave(request, orderVariantRepository, orderVariantMapper);
    }

    @Override
    public OrderVariantResponse save(OrderVariantKey id, OrderVariantRequest request) {
        return defaultSave(id, request, orderVariantRepository, orderVariantMapper, ResourceNameConst.ORDER_VARIANT);
    }

    @Override
    public void delete(OrderVariantKey id) {
        orderVariantRepository.deleteById(id);
    }

    @Override
    public void delete(List<OrderVariantKey> ids) {
        orderVariantRepository.deleteAllById(ids);
    }

}
