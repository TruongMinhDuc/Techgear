package com.techgear.techgear_be.dtos.client;

import com.techgear.techgear_be.models.reward.RewardType;
import lombok.Data;

import java.time.Instant;

@Data
public class ClientRewardLogResponse {
    private Long rewardLogId;
    private Instant rewardLogCreatedAt;
    private Integer rewardLogScore;
    private RewardType rewardLogType;
    private String rewardLogNote;
}
