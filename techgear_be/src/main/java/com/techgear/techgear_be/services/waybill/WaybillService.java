package com.techgear.techgear_be.services.waybill;

import com.techgear.techgear_be.dtos.waybill.ghn.GhnCallbackOrderRequest;
import com.techgear.techgear_be.dtos.waybill.WaybillRequest;
import com.techgear.techgear_be.dtos.waybill.WaybillResponse;
import com.techgear.techgear_be.services.CrudService;

public interface WaybillService extends CrudService<Long, WaybillRequest, WaybillResponse> {

    void callbackStatusWaybillFromGHN(GhnCallbackOrderRequest ghnCallbackOrderRequest);

}
