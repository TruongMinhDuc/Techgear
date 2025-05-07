package com.techgear.techgear_be.services.inventory;

import com.techgear.techgear_be.dtos.inventory.DocketRequest;
import com.techgear.techgear_be.dtos.inventory.DocketResponse;
import com.techgear.techgear_be.services.CrudService;

public interface DocketService extends CrudService<Long, DocketRequest, DocketResponse> {}
