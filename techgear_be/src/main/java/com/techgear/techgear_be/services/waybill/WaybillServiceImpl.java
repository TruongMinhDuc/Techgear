package com.techgear.techgear_be.services.waybill;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techgear.techgear_be.constants.FieldNameConst;
import com.techgear.techgear_be.constants.ResourceNameConst;
import com.techgear.techgear_be.constants.SearchFieldsConst;
import com.techgear.techgear_be.dtos.CollectionWrapper;
import com.techgear.techgear_be.dtos.ListResponse;
import com.techgear.techgear_be.dtos.waybill.*;
import com.techgear.techgear_be.dtos.waybill.ghn.*;
import com.techgear.techgear_be.models.cashbook.PaymentMethodType;
import com.techgear.techgear_be.models.general.Notification;
import com.techgear.techgear_be.models.general.NotificationType;
import com.techgear.techgear_be.models.order.Order;
import com.techgear.techgear_be.models.order.OrderVariant;
import com.techgear.techgear_be.models.waybill.Waybill;
import com.techgear.techgear_be.models.waybill.WaybillLog;
import com.techgear.techgear_be.exceptions.ResourceNotFoundException;
import com.techgear.techgear_be.mappers.general.NotificationMapper;
import com.techgear.techgear_be.mappers.waybill.WaybillMapper;
import com.techgear.techgear_be.repositories.general.NotificationRepository;
import com.techgear.techgear_be.repositories.order.OrderRepository;
import com.techgear.techgear_be.repositories.waybill.WaybillLogRepository;
import com.techgear.techgear_be.repositories.waybill.WaybillRepository;
import com.techgear.techgear_be.services.general.NotificationService;
import com.techgear.techgear_be.utils.RewardUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class WaybillServiceImpl implements WaybillService {

    @Value("${techgear.app.shipping.ghnToken}")
    private String ghnToken;
    @Value("${techgear.app.shipping.ghnShopId}")
    private String ghnShopId;
    @Value("${techgear.app.shipping.ghnApiPath}")
    private String ghnApiPath;

    private final OrderRepository orderRepository;
    private final WaybillRepository waybillRepository;
    private final WaybillMapper waybillMapper;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final WaybillLogRepository waybillLogRepository;
    private final RewardUtils rewardUtils;

    @Override
    public ListResponse<WaybillResponse> findAll(int page, int size, String sort, String filter, String search, boolean all) {
        return defaultFindAll(page, size, sort, filter, search, all, SearchFieldsConst.WAYBILL, waybillRepository, waybillMapper);
    }

    @Override
    public WaybillResponse findById(Long id) {
        return defaultFindById(id, waybillRepository, waybillMapper, ResourceNameConst.WAYBILL);
    }

    @Override
    public WaybillResponse save(WaybillRequest waybillRequest) {
        Optional<Waybill> waybillOpt = waybillRepository.findByOrderId(waybillRequest.getOrderId());

        if (waybillOpt.isPresent()) {
            throw new RuntimeException("This order already had a waybill. Please choose another order!");
        }

        Order order = orderRepository.findById(waybillRequest.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNameConst.ORDER, FieldNameConst.ID, waybillRequest.getOrderId()));

        if (order.getStatus() == 1) {
            String createGhnOrderApiPath = ghnApiPath + "/shipping-order/create";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.add("Token", ghnToken);
            headers.add("ShopId", ghnShopId);

            RestTemplate restTemplate = new RestTemplate();

            var request = new HttpEntity<>(buildGhnCreateOrderRequest(waybillRequest, order), headers);
            var response = restTemplate.postForEntity(createGhnOrderApiPath, request, GhnCreateOrderResponse.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Error when calling Create Order GHN API");
            }

            if (response.getBody() != null) {
                var ghnCreateOrderResponse = response.getBody();

                Waybill waybill = waybillMapper.requestToEntity(waybillRequest);

                waybill.setCode(ghnCreateOrderResponse.getData().getOrderCode());
                waybill.setOrder(order);
                waybill.setExpectedDeliveryTime(ghnCreateOrderResponse.getData().getExpectedDeliveryTime());
                waybill.setStatus(1);
                waybill.setCodAmount(
                        order.getPaymentMethodType() == PaymentMethodType.CASH
                                ? order.getTotalPay().intValue()
                                : 0
                );
                waybill.setShippingFee(ghnCreateOrderResponse.getData().getTotalFee());
                waybill.setGhnPaymentTypeId(chooseGhnPaymentTypeId(order.getPaymentMethodType()));

                Waybill waybillAfterSave = waybillRepository.save(waybill);


                order.setShippingCost(BigDecimal.valueOf(ghnCreateOrderResponse.getData().getTotalFee()));
                order.setTotalPay(BigDecimal.valueOf(
                        order.getTotalPay().intValue() + ghnCreateOrderResponse.getData().getTotalFee()));
                order.setStatus(2);
                orderRepository.save(order);

                WaybillLog waybillLog = new WaybillLog();
                waybillLog.setWaybill(waybillAfterSave);
                waybillLog.setCurrentStatus(1);

                waybillLogRepository.save(waybillLog);

                Notification notification = new Notification()
                        .setUser(order.getUser())
                        .setType(NotificationType.ORDER)
                        .setMessage(
                                order.getPaymentMethodType() == PaymentMethodType.CASH
                                        ? String.format(
                                        "Đơn hàng %s của bạn đã được duyệt. Phí vận chuyển là %s. Tổng tiền cần trả là %s.",
                                        order.getCode(),
                                        NumberFormat.getCurrencyInstance(new Locale("vi", "VN"))
                                                .format(order.getShippingCost()),
                                        NumberFormat.getCurrencyInstance(new Locale("vi", "VN"))
                                                .format(order.getTotalPay()))
                                        : String.format("Đơn hàng %s của bạn đã được duyệt.", order.getCode())
                        )
                        .setAnchor("/order/detail/" + order.getCode())
                        .setStatus(1);

                notificationRepository.save(notification);

                notificationService.pushNotification(order.getUser().getUsername(),
                        notificationMapper.entityToResponse(notification));

                return waybillMapper.entityToResponse(waybillAfterSave);
            } else {
                throw new RuntimeException("Response from Create Order GHN API cannot use");
            }
        } else {
            throw new RuntimeException("Cannot create a new waybill. Order already had a waybill or was cancelled before.");
        }
    }

    @Override
    public WaybillResponse save(Long id, WaybillRequest waybillRequest) {
        Waybill waybill = waybillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNameConst.WAYBILL, FieldNameConst.ID, id));

        String updateGhnOrderApiPath = ghnApiPath + "/shipping-order/update";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("Token", ghnToken);
        headers.add("ShopId", ghnShopId);

        RestTemplate restTemplate = new RestTemplate();

        var request = new HttpEntity<>(buildGhnUpdateOrderRequest(waybillRequest, waybill), headers);
        var response = restTemplate.postForEntity(updateGhnOrderApiPath, request, GhnUpdateOrderResponse.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error when calling Update Order GHN API");
        }

        if (response.getBody() != null) {
            Waybill waybillAfterSave = waybillRepository.save(waybillMapper.partialUpdate(waybill, waybillRequest));
            return waybillMapper.entityToResponse(waybillAfterSave);
        } else {
            throw new RuntimeException("Response from Update Order GHN API cannot use");
        }
    }

    @Override
    public void delete(Long id) {
        waybillRepository.deleteById(id);
    }

    @Override
    public void delete(List<Long> ids) {
        waybillRepository.deleteAllById(ids);
    }

    private GhnCreateOrderRequest buildGhnCreateOrderRequest(WaybillRequest waybillRequest, Order order) {
        GhnCreateOrderRequest ghnCreateOrderRequest = new GhnCreateOrderRequest();

        ghnCreateOrderRequest.setPaymentTypeId(chooseGhnPaymentTypeId(order.getPaymentMethodType()));
        ghnCreateOrderRequest.setNote(waybillRequest.getNote());
        ghnCreateOrderRequest.setRequiredNote(waybillRequest.getGhnRequiredNote());
        ghnCreateOrderRequest.setToName(order.getToName());
        ghnCreateOrderRequest.setToPhone(order.getToPhone());
        ghnCreateOrderRequest.setToAddress(order.getToAddress());
        ghnCreateOrderRequest.setToWardName(order.getToWardName());
        ghnCreateOrderRequest.setToDistrictName(order.getToDistrictName());
        ghnCreateOrderRequest.setToProvinceName(order.getToProvinceName());
        ghnCreateOrderRequest.setCodAmount(
                order.getPaymentMethodType() == PaymentMethodType.CASH
                        ? order.getTotalPay().intValue()
                        : 0
        );
        ghnCreateOrderRequest.setWeight(waybillRequest.getWeight());
        ghnCreateOrderRequest.setLength(waybillRequest.getLength());
        ghnCreateOrderRequest.setWidth(waybillRequest.getWidth());
        ghnCreateOrderRequest.setHeight(waybillRequest.getHeight());
        ghnCreateOrderRequest.setServiceTypeId(2);
        ghnCreateOrderRequest.setServiceId(0);
        ghnCreateOrderRequest.setPickupTime(waybillRequest.getShippingDate().getEpochSecond());

        List<GhnCreateOrderRequest.Item> items = new ArrayList<>();
        for (OrderVariant orderVariant : order.getOrderVariants()) {
            var item = new GhnCreateOrderRequest.Item();
            item.setName(buildGhnProductName(orderVariant.getVariant().getProduct().getName(),
                    orderVariant.getVariant().getProperties()));
            item.setQuantity(orderVariant.getQuantity());
            item.setPrice(orderVariant.getPrice().intValue());
            items.add(item);
        }
        ghnCreateOrderRequest.setItems(items);

        return ghnCreateOrderRequest;
    }
    @SuppressWarnings("unchecked")
    private String buildGhnProductName(String productName, @Nullable JsonNode variantProperties) {
        ObjectMapper mapper = new ObjectMapper();

        CollectionWrapper<LinkedHashMap<String, Object>> variantPropertiesObj;

        try {
            variantPropertiesObj = mapper.treeToValue(variantProperties, CollectionWrapper.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot build product name for GHN order");
        }

        if (variantPropertiesObj == null) {
            return productName;
        }

        StringJoiner joiner = new StringJoiner(", ", "(", ")");

        for (var variantProperty : variantPropertiesObj.getContent()) {
            joiner.add(String.format("%s: %s", variantProperty.get("name"), variantProperty.get("value")));
        }

        return String.format("%s %s", productName, joiner);
    }

    private int chooseGhnPaymentTypeId(PaymentMethodType paymentMethodType) {
        return paymentMethodType == PaymentMethodType.CASH
                ? 2
                : 1;
    }

    private GhnUpdateOrderRequest buildGhnUpdateOrderRequest(WaybillRequest waybillRequest, Waybill waybill) {
        GhnUpdateOrderRequest ghnUpdateOrderRequest = new GhnUpdateOrderRequest();

        ghnUpdateOrderRequest.setOrderCode(waybill.getCode());
        ghnUpdateOrderRequest.setNote(waybillRequest.getNote());
        ghnUpdateOrderRequest.setRequiredNote(waybillRequest.getGhnRequiredNote());

        return ghnUpdateOrderRequest;
    }

    @Override
    public void callbackStatusWaybillFromGHN(GhnCallbackOrderRequest ghnCallbackOrderRequest) {
        if (Objects.equals(ghnCallbackOrderRequest.getShopID().toString(), ghnShopId)) {
            Waybill waybill = waybillRepository.findByCode(ghnCallbackOrderRequest.getOrderCode())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            ResourceNameConst.WAYBILL, FieldNameConst.WAYBILL_CODE, ghnCallbackOrderRequest.getOrderCode()));

            Order order = waybill.getOrder();

            WaybillLog waybillLog = new WaybillLog();
            waybillLog.setWaybill(waybill);
            waybillLog.setPreviousStatus(waybill.getStatus());

            int currentWaybillStatus = WaybillCallbackConstants.WAYBILL_STATUS_CODE
                    .get(ghnCallbackOrderRequest.getStatus());

            if (!waybill.getStatus().equals(currentWaybillStatus)) {
                switch (currentWaybillStatus) {
                    case WaybillCallbackConstants.WAITING:
                        waybillLog.setCurrentStatus(1);
                        waybill.setStatus(1);
                        order.setStatus(2);
                        break;
                    case WaybillCallbackConstants.SHIPPING:
                        createNotification(new Notification()
                                .setUser(order.getUser())
                                .setType(NotificationType.ORDER)
                                .setMessage(String.format("Đơn hàng %s của bạn đang được vận chuyển.", order.getCode()))
                                .setAnchor("/order/detail/" + order.getCode())
                                .setStatus(1));
                        waybillLog.setCurrentStatus(2);
                        waybill.setStatus(2);
                        order.setStatus(3);
                        break;
                    case WaybillCallbackConstants.SUCCESS:
                        createNotification(new Notification()
                                .setUser(order.getUser())
                                .setType(NotificationType.ORDER)
                                .setMessage(String.format("Đơn hàng %s của bạn đã giao thành công!", order.getCode()))
                                .setAnchor("/order/detail/" + order.getCode())
                                .setStatus(1));
                        waybillLog.setCurrentStatus(3);
                        waybill.setStatus(3);
                        order.setStatus(4);
                        order.setPaymentStatus(2);

                        rewardUtils.successOrderHook(order);
                        break;
                    case WaybillCallbackConstants.FAILED:
                    case WaybillCallbackConstants.RETURN:
                        createNotification(new Notification()
                                .setUser(order.getUser())
                                .setType(NotificationType.ORDER)
                                .setMessage(String.format("Đơn hàng %s của bạn đã bị hủy.", order.getCode()))
                                .setAnchor("/order/detail/" + order.getCode())
                                .setStatus(1));
                        waybillLog.setCurrentStatus(4);
                        waybill.setStatus(4);
                        order.setStatus(5);
                        break;
                    default:
                        throw new RuntimeException("There is no waybill status corresponding to GHN status code");
                }

                waybillRepository.save(waybill);
                orderRepository.save(order);
                waybillLogRepository.save(waybillLog);
            }
        } else {
            throw new RuntimeException("ShopId is not valid");
        }
    }

    private void createNotification(Notification notification) {
        notificationRepository.save(notification);

        notificationService.pushNotification(notification.getUser().getUsername(),
                notificationMapper.entityToResponse(notification));
    }

}
