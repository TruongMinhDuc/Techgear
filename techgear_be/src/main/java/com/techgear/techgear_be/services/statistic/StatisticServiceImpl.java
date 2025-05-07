package com.techgear.techgear_be.services.statistic;

import com.techgear.techgear_be.dtos.statistic.StatisticResource;
import com.techgear.techgear_be.dtos.statistic.StatisticResponse;
import com.techgear.techgear_be.repositories.authentication.UserProjectionRepository;
import com.techgear.techgear_be.repositories.customer.CustomerRepository;
import com.techgear.techgear_be.repositories.order.OrderProjectionRepository;
import com.techgear.techgear_be.repositories.order.OrderRepository;
import com.techgear.techgear_be.repositories.product.BrandRepository;
import com.techgear.techgear_be.repositories.product.ProductRepository;
import com.techgear.techgear_be.repositories.product.SupplierRepository;
import com.techgear.techgear_be.repositories.promotion.PromotionRepository;
import com.techgear.techgear_be.repositories.review.ReviewProjectionRepository;
import com.techgear.techgear_be.repositories.review.ReviewRepository;
import com.techgear.techgear_be.repositories.waybill.WaybillProjectionRepository;
import com.techgear.techgear_be.repositories.waybill.WaybillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private WaybillRepository waybillRepository;
    private PromotionRepository promotionRepository;
    private SupplierRepository supplierRepository;
    private BrandRepository brandRepository;
    private ReviewRepository reviewRepository;
    private UserProjectionRepository userProjectionRepository;
    private OrderProjectionRepository orderProjectionRepository;
    private WaybillProjectionRepository waybillProjectionRepository;
    private ReviewProjectionRepository reviewProjectionRepository;

    @Override
    public StatisticResponse getStatistic() {
        StatisticResponse statisticResponse = new StatisticResponse();

        int totalCustomer = customerRepository.countByCustomerId();
        int totalProduct = productRepository.countByProductId();
        int totalOrder = orderRepository.countByOrderId();
        int totalWaybill = waybillRepository.countByWaybillId();
        int totalReview = reviewRepository.countByReviewId();
        int totalActivePromotion = promotionRepository.countByPromotionId();
        int totalSupplier = supplierRepository.countBySupplierId();
        int totalBrand = brandRepository.countByBrandId();

        List<StatisticResource> statisticRegistration = userProjectionRepository.getUserCountByCreateDate();
        List<StatisticResource> statisticOrder = orderProjectionRepository.getOrderCountByCreateDate();
        List<StatisticResource> statisticReview = reviewProjectionRepository.getReviewCountByCreateDate();
        List<StatisticResource> statisticWaybill = waybillProjectionRepository.getWaybillCountByCreateDate();

        statisticResponse.setTotalCustomer(totalCustomer);
        statisticResponse.setTotalProduct(totalProduct);
        statisticResponse.setTotalOrder(totalOrder);
        statisticResponse.setTotalWaybill(totalWaybill);
        statisticResponse.setTotalReview(totalReview);
        statisticResponse.setTotalActivePromotion(totalActivePromotion);
        statisticResponse.setTotalSupplier(totalSupplier);
        statisticResponse.setTotalBrand(totalBrand);
        statisticResponse.setStatisticRegistration(statisticRegistration);
        statisticResponse.setStatisticOrder(statisticOrder);
        statisticResponse.setStatisticReview(statisticReview);
        statisticResponse.setStatisticWaybill(statisticWaybill);

        return statisticResponse;
    }

}
