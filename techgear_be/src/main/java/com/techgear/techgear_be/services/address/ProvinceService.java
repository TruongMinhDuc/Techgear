package com.techgear.techgear_be.services.address;

import com.techgear.techgear_be.dtos.address.province.ProvinceRequest;
import com.techgear.techgear_be.dtos.address.province.ProvinceResponse;
import com.techgear.techgear_be.services.CrudService;

public interface ProvinceService extends CrudService<Long, ProvinceRequest, ProvinceResponse> {}
