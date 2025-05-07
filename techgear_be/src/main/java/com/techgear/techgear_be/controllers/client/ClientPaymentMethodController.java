package com.techgear.techgear_be.controllers.client;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.CollectionWrapper;
import com.techgear.techgear_be.dtos.cashbook.ClientPaymentMethodResponse;
import com.techgear.techgear_be.mappers.cashbook.PaymentMethodMapper;
import com.techgear.techgear_be.repositories.cashbook.PaymentMethodRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client-api/payment-methods")
@AllArgsConstructor
@CrossOrigin(ApplicationConst.FRONTEND_HOST)
public class ClientPaymentMethodController {

    private PaymentMethodRepository paymentMethodRepository;
    private PaymentMethodMapper paymentMethodMapper;

    @GetMapping
    public ResponseEntity<CollectionWrapper<ClientPaymentMethodResponse>> getAllPaymentMethods() {
        List<ClientPaymentMethodResponse> clientPaymentMethodResponses = paymentMethodMapper
                .entityToClientResponse(paymentMethodRepository.findAllByStatus(1));
        return ResponseEntity.status(HttpStatus.OK).body(CollectionWrapper.of(clientPaymentMethodResponses));
    }

}
