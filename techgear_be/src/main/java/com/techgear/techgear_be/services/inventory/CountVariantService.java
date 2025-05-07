package com.techgear.techgear_be.services.inventory;

import com.techgear.techgear_be.dtos.inventory.CountVariantRequest;
import com.techgear.techgear_be.dtos.inventory.CountVariantResponse;
import com.techgear.techgear_be.models.inventory.CountVariantKey;
import com.techgear.techgear_be.services.CrudService;

public interface CountVariantService extends CrudService<CountVariantKey, CountVariantRequest, CountVariantResponse> {}
