package com.techgear.techgear_be.controllers.waybill;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.waybill.ghn.GhnCallbackOrderRequest;
import com.techgear.techgear_be.services.waybill.WaybillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/waybills")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class WaybillController {

    private WaybillService waybillService;

    @PutMapping("/callback-ghn")
    public ResponseEntity<ObjectNode> callbackStatusWaybillFromGHN(@RequestBody GhnCallbackOrderRequest ghnCallbackOrderRequest) {
        waybillService.callbackStatusWaybillFromGHN(ghnCallbackOrderRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

}
