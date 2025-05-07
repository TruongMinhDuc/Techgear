package com.techgear.techgear_be.mappers.cashbook;

import com.techgear.techgear_be.dtos.cashbook.ClientPaymentMethodResponse;
import com.techgear.techgear_be.dtos.cashbook.PaymentMethodRequest;
import com.techgear.techgear_be.dtos.cashbook.PaymentMethodResponse;
import com.techgear.techgear_be.models.cashbook.PaymentMethod;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMethodMapper extends GenericMapper<PaymentMethod, PaymentMethodRequest, PaymentMethodResponse> {

    @Mapping(source = "id", target = "paymentMethodId")
    @Mapping(source = "name", target = "paymentMethodName")
    @Mapping(source = "code", target = "paymentMethodCode")
    ClientPaymentMethodResponse entityToClientResponse(PaymentMethod entity);

    List<ClientPaymentMethodResponse> entityToClientResponse(List<PaymentMethod> entities);

}
