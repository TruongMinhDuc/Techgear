package com.techgear.techgear_be.services.inventory;

import com.techgear.techgear_be.dtos.inventory.DocketVariantRequest;
import com.techgear.techgear_be.dtos.inventory.DocketVariantResponse;
import com.techgear.techgear_be.models.inventory.DocketVariantKey;
import com.techgear.techgear_be.services.CrudService;

public interface DocketVariantService extends CrudService<DocketVariantKey, DocketVariantRequest, DocketVariantResponse> {}
