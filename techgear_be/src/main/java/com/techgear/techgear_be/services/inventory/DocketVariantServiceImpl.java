package com.techgear.techgear_be.services.inventory;

import com.techgear.techgear_be.constants.ResourceNameConst;
import com.techgear.techgear_be.constants.SearchFieldsConst;
import com.techgear.techgear_be.dtos.ListResponse;
import com.techgear.techgear_be.dtos.inventory.DocketVariantRequest;
import com.techgear.techgear_be.dtos.inventory.DocketVariantResponse;
import com.techgear.techgear_be.models.inventory.DocketVariantKey;
import com.techgear.techgear_be.mappers.inventory.DocketVariantMapper;
import com.techgear.techgear_be.repositories.inventory.DocketVariantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DocketVariantServiceImpl implements DocketVariantService {

    private DocketVariantRepository docketVariantRepository;

    private DocketVariantMapper docketVariantMapper;

    @Override
    public ListResponse<DocketVariantResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return defaultFindAll(page, size, sort, filter, search, all, SearchFieldsConst.DOCKET_VARIANT, docketVariantRepository, docketVariantMapper);
    }

    @Override
    public DocketVariantResponse findById(DocketVariantKey id) {
        return defaultFindById(id, docketVariantRepository, docketVariantMapper, ResourceNameConst.DOCKET_VARIANT);
    }

    @Override
    public DocketVariantResponse save(DocketVariantRequest request) {
        return defaultSave(request, docketVariantRepository, docketVariantMapper);
    }

    @Override
    public DocketVariantResponse save(DocketVariantKey id, DocketVariantRequest request) {
        return defaultSave(id, request, docketVariantRepository, docketVariantMapper, ResourceNameConst.DOCKET_VARIANT);
    }

    @Override
    public void delete(DocketVariantKey id) {
        docketVariantRepository.deleteById(id);
    }

    @Override
    public void delete(List<DocketVariantKey> ids) {
        docketVariantRepository.deleteAllById(ids);
    }

}
