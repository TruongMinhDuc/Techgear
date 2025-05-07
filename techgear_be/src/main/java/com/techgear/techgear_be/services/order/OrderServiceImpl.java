package com.techgear.techgear_be.services.order;

import com.techgear.techgear_be.configs.payment.paypal.PayPalHttpClient;
import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.constants.FieldNameConst;
import com.techgear.techgear_be.constants.ResourceNameConst;
import com.techgear.techgear_be.dtos.client.ClientConfirmedOrderResponse;
import com.techgear.techgear_be.dtos.client.ClientSimpleOrderRequest;
import com.techgear.techgear_be.dtos.payment.*;
import com.techgear.techgear_be.dtos.waybill.ghn.GhnCancelOrderRequest;
import com.techgear.techgear_be.dtos.waybill.ghn.GhnCancelOrderResponse;
import com.techgear.techgear_be.models.authentication.User;
import com.techgear.techgear_be.models.cart.Cart;
import com.techgear.techgear_be.models.cashbook.PaymentMethodType;
import com.techgear.techgear_be.models.general.Notification;
import com.techgear.techgear_be.models.general.NotificationType;
import com.techgear.techgear_be.models.order.Order;
import com.techgear.techgear_be.models.order.OrderResource;
import com.techgear.techgear_be.models.order.OrderVariant;
import com.techgear.techgear_be.models.promotion.Promotion;
import com.techgear.techgear_be.models.waybill.Waybill;
import com.techgear.techgear_be.models.waybill.WaybillLog;
import com.techgear.techgear_be.exceptions.ResourceNotFoundException;
import com.techgear.techgear_be.mappers.client.ClientOrderMapper;
import com.techgear.techgear_be.mappers.general.NotificationMapper;
import com.techgear.techgear_be.repositories.authentication.UserRepository;
import com.techgear.techgear_be.repositories.cart.CartRepository;
import com.techgear.techgear_be.repositories.general.NotificationRepository;
import com.techgear.techgear_be.repositories.order.OrderRepository;
import com.techgear.techgear_be.repositories.promotion.PromotionRepository;
import com.techgear.techgear_be.repositories.waybill.WaybillLogRepository;
import com.techgear.techgear_be.repositories.waybill.WaybillRepository;
import com.techgear.techgear_be.services.general.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Value("${techgear.app.shipping.ghnToken}")
    private String ghnToken;
    @Value("${techgear.app.shipping.ghnShopId}")
    private String ghnShopId;
    @Value("${techgear.app.shipping.ghnApiPath}")
    private String ghnApiPath;

    private final OrderRepository orderRepository;
    private final WaybillRepository waybillRepository;
    private final WaybillLogRepository waybillLogRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PromotionRepository promotionRepository;

    private final PayPalHttpClient payPalHttpClient;
    private final ClientOrderMapper clientOrderMapper;

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    private static final int USD_VND_RATE = 26_000;

    @Override
    public void cancelOrder(String code) {
        Order order = orderRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNameConst.ORDER, FieldNameConst.ORDER_CODE, code));

        if (order.getStatus() < 3) {
            order.setStatus(5);
            orderRepository.save(order);

            Waybill waybill = waybillRepository.findByOrderId(order.getId()).orElse(null);

            if (waybill != null && waybill.getStatus() == 1) {
                String cancelOrderApiPath = ghnApiPath + "/switch-status/cancel";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("Token", ghnToken);
                headers.add("ShopId", ghnShopId);

                RestTemplate restTemplate = new RestTemplate();

                var request = new HttpEntity<>(new GhnCancelOrderRequest(List.of(waybill.getCode())), headers);
                var response = restTemplate.postForEntity(cancelOrderApiPath, request, GhnCancelOrderResponse.class);

                if (response.getStatusCode() != HttpStatus.OK) {
                    throw new RuntimeException("Error when calling Cancel Order GHN API");
                }

                if (response.getBody() != null) {
                    for (var data : response.getBody().getData()) {
                        if (data.getResult()) {
                            WaybillLog waybillLog = new WaybillLog();
                            waybillLog.setWaybill(waybill);
                            waybillLog.setPreviousStatus(waybill.getStatus());
                            waybillLog.setCurrentStatus(4);
                            waybillLogRepository.save(waybillLog);

                            waybill.setStatus(4);
                            waybillRepository.save(waybill);
                        }
                    }
                }
            }
        } else {
            throw new RuntimeException(String
                    .format("Order with code %s is in delivery or has been cancelled. Please check again!", code));
        }
    }

    @Override
    public ClientConfirmedOrderResponse createClientOrder(ClientSimpleOrderRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNameConst.CART, FieldNameConst.USERNAME, username));

        Order order = new Order();

        order.setCode(RandomString.make(12).toUpperCase());
        order.setStatus(1);
        order.setToName(user.getFullname());
        order.setToPhone(user.getPhone());
        order.setToAddress(user.getAddress().getLine());
        order.setToWardName(user.getAddress().getWard().getName());
        order.setToDistrictName(user.getAddress().getDistrict().getName());
        order.setToProvinceName(user.getAddress().getProvince().getName());
        order.setOrderResource((OrderResource) new OrderResource().setId(1L));
        order.setUser(user);

        order.setOrderVariants(cart.getCartVariants().stream()
                .map(cartVariant -> {
                    Promotion promotion = promotionRepository
                            .findActivePromotionByProductId(cartVariant.getVariant().getProduct().getId())
                            .stream()
                            .findFirst()
                            .orElse(null);

                    double currentPrice = calculateDiscountedPrice(cartVariant.getVariant().getPrice(),
                            promotion == null ? 0 : promotion.getPercent());

                    return new OrderVariant()
                            .setOrder(order)
                            .setVariant(cartVariant.getVariant())
                            .setPrice(BigDecimal.valueOf(currentPrice))
                            .setQuantity(cartVariant.getQuantity())
                            .setAmount(BigDecimal.valueOf(currentPrice).multiply(BigDecimal.valueOf(cartVariant.getQuantity())));
                })
                .collect(Collectors.toSet()));


        BigDecimal totalAmount = BigDecimal.valueOf(order.getOrderVariants().stream()
                .mapToDouble(orderVariant -> orderVariant.getAmount().doubleValue())
                .sum());

        BigDecimal tax = BigDecimal.valueOf(ApplicationConst.DEFAULT_TAX);

        BigDecimal shippingCost = BigDecimal.ZERO;

        BigDecimal totalPay = totalAmount
                .add(totalAmount.multiply(tax).setScale(0, RoundingMode.HALF_UP))
                .add(shippingCost);

        order.setTotalAmount(totalAmount);
        order.setTax(tax);
        order.setShippingCost(shippingCost);
        order.setTotalPay(totalPay);
        order.setPaymentMethodType(request.getPaymentMethodType());
        order.setPaymentStatus(1);

        ClientConfirmedOrderResponse response = new ClientConfirmedOrderResponse();

        response.setOrderCode(order.getCode());
        response.setOrderPaymentMethodType(order.getPaymentMethodType());

        if (request.getPaymentMethodType() == PaymentMethodType.CASH) {
            orderRepository.save(order);
        } else if (request.getPaymentMethodType() == PaymentMethodType.PAYPAL) {
            try {
                BigDecimal totalPayUSD = order.getTotalPay()
                        .divide(BigDecimal.valueOf(USD_VND_RATE), 0, RoundingMode.HALF_UP);

                PaypalRequest paypalRequest = new PaypalRequest();

                paypalRequest.setIntent(OrderIntent.CAPTURE);
                paypalRequest.setPurchaseUnits(List.of(
                        new PaypalRequest.PurchaseUnit(
                                new PaypalRequest.PurchaseUnit.Money("USD", totalPayUSD.toString())
                        )
                ));

                paypalRequest.setApplicationContext(new PaypalRequest.PayPalAppContext()
                        .setBrandName("techgear")
                        .setLandingPage(PaymentLandingPage.BILLING)
                        .setReturnUrl(ApplicationConst.BACKEND_HOST + "/client-api/orders/success")
                        .setCancelUrl(ApplicationConst.BACKEND_HOST + "/client-api/orders/cancel"));

                PaypalResponse paypalResponse = payPalHttpClient.createPaypalTransaction(paypalRequest);

                order.setPaypalOrderId(paypalResponse.getId());
                order.setPaypalOrderStatus(paypalResponse.getStatus().toString());

                orderRepository.save(order);

                for (PaypalResponse.Link link : paypalResponse.getLinks()) {
                    if ("approve".equals(link.getRel())) {
                        response.setOrderPaypalCheckoutLink(link.getHref());
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Cannot create PayPal transaction request!" + e);
            }
        } else {
            throw new RuntimeException("Cannot identify payment method");
        }

        cart.setStatus(2);
        cartRepository.save(cart);

        return response;
    }

    @Override
    public void captureTransactionPaypal(String paypalOrderId, String payerId) {
        Order order = orderRepository.findByPaypalOrderId(paypalOrderId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNameConst.ORDER, FieldNameConst.PAYPAL_ORDER_ID, paypalOrderId));

        order.setPaypalOrderStatus(OrderStatus.APPROVED.toString());

        try {
            payPalHttpClient.capturePaypalTransaction(paypalOrderId, payerId);

            order.setPaypalOrderStatus(OrderStatus.COMPLETED.toString());
            order.setPaymentStatus(2);

            Notification notification = new Notification()
                    .setUser(order.getUser())
                    .setType(NotificationType.CHECKOUT_PAYPAL_SUCCESS)
                    .setMessage(String.format("Đơn hàng %s của bạn đã được thanh toán thành công bằng PayPal.", order.getCode()))
                    .setAnchor("/order/detail/" + order.getCode())
                    .setStatus(1);

            notificationRepository.save(notification);

            notificationService.pushNotification(order.getUser().getUsername(),
                    notificationMapper.entityToResponse(notification));
        } catch (Exception e) {
            log.error("Cannot capture transaction: {0}", e);
        }

        orderRepository.save(order);
    }

    private Double calculateDiscountedPrice(Double price, Integer discount) {
        return price * (100 - discount) / 100;
    }

}
