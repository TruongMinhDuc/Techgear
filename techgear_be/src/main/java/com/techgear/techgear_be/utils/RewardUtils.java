package com.techgear.techgear_be.utils;

import com.techgear.techgear_be.models.general.Notification;
import com.techgear.techgear_be.models.general.NotificationType;
import com.techgear.techgear_be.models.order.Order;
import com.techgear.techgear_be.models.review.Review;
import com.techgear.techgear_be.models.reward.RewardLog;
import com.techgear.techgear_be.models.reward.RewardStrategy;
import com.techgear.techgear_be.models.reward.RewardType;
import com.techgear.techgear_be.mappers.general.NotificationMapper;
import com.techgear.techgear_be.repositories.general.NotificationRepository;
import com.techgear.techgear_be.repositories.reward.RewardLogRepository;
import com.techgear.techgear_be.repositories.reward.RewardStrategyRepository;
import com.techgear.techgear_be.services.general.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class RewardUtils {

    private RewardStrategyRepository rewardStrategyRepository;
    private RewardLogRepository rewardLogRepository;
    private NotificationRepository notificationRepository;
    private NotificationService notificationService;
    private NotificationMapper notificationMapper;

    private static final ExpressionParser spelParser = new SpelExpressionParser();

    public void approveReviewHook(Review review) {
        if (review.getStatus().equals(2)) {
            Optional<RewardStrategy> addReviewRewardStrategy = rewardStrategyRepository
                    .findByCodeAndStatus(RewardType.ADD_REVIEW, 1);

            if (addReviewRewardStrategy.isPresent()) {
                Integer score = spelParser.parseExpression(addReviewRewardStrategy.get().getFormula())
                        .getValue(Integer.class);

                String note = String.format("Bạn đã nhận được %s điểm thưởng cho đánh giá ở sản phẩm %s.",
                        score,
                        review.getProduct().getName()
                );

                RewardLog rewardLog = new RewardLog()
                        .setUser(review.getUser())
                        .setType(RewardType.ADD_REVIEW)
                        .setScore(score)
                        .setNote(note);

                rewardLogRepository.save(rewardLog);

                Notification notification = new Notification()
                        .setUser(review.getUser())
                        .setType(NotificationType.REVIEW)
                        .setMessage(note)
                        .setAnchor("/user/reward")
                        .setStatus(1);

                notificationRepository.save(notification);

                notificationService.pushNotification(review.getUser().getUsername(),
                        notificationMapper.entityToResponse(notification));
            }
        }
    }

    public void successOrderHook(Order order) {
        if (order.getStatus().equals(4) && order.getPaymentStatus().equals(2)) {
            Optional<RewardStrategy> successOrderRewardStrategy = rewardStrategyRepository
                    .findByCodeAndStatus(RewardType.SUCCESS_ORDER, 1);

            if (successOrderRewardStrategy.isPresent()) {
                Integer score = spelParser.parseExpression(successOrderRewardStrategy.get().getFormula()
                                .replace("{{ORDER_TOTAL_PAY}}", order.getTotalPay().toString()))
                        .getValue(Integer.class);

                String note = String.format("Bạn đã nhận được %s điểm thưởng cho đơn hàng %s.",
                        score,
                        order.getCode()
                );

                RewardLog rewardLog = new RewardLog()
                        .setUser(order.getUser())
                        .setType(RewardType.SUCCESS_ORDER)
                        .setScore(score)
                        .setNote(note);

                rewardLogRepository.save(rewardLog);

                Notification notification = new Notification()
                        .setUser(order.getUser())
                        .setType(NotificationType.ORDER)
                        .setMessage(note)
                        .setAnchor("/user/reward")
                        .setStatus(1);

                notificationRepository.save(notification);

                notificationService.pushNotification(order.getUser().getUsername(),
                        notificationMapper.entityToResponse(notification));
            }
        }
    }

}
