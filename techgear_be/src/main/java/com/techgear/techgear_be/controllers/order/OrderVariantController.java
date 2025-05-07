package com.techgear.techgear_be.controllers.order;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.order.OrderVariantKeyRequest;
import com.techgear.techgear_be.models.order.OrderVariantKey;
import com.techgear.techgear_be.services.inventory.OrderVariantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order-variants")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class OrderVariantController {

    private OrderVariantService orderVariantService;

    @DeleteMapping("/{orderId}/{variantId}")
    public ResponseEntity<Void> deleteOrderVariant(@PathVariable("orderId") Long orderId,
                                                   @PathVariable("variantId") Long variantId) {
        OrderVariantKey id = new OrderVariantKey(orderId, variantId);
        orderVariantService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOrderVariants(@RequestBody List<OrderVariantKeyRequest> idRequests) {
        List<OrderVariantKey> ids = idRequests.stream()
                .map(idRequest -> new OrderVariantKey(idRequest.getOrderId(), idRequest.getVariantId()))
                .collect(Collectors.toList());
        orderVariantService.delete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
