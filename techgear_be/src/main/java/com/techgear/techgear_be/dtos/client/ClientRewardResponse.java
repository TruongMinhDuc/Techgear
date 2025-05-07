package com.techgear.techgear_be.dtos.client;

import lombok.Data;

import java.util.List;

@Data
public class ClientRewardResponse {
    private Integer rewardTotalScore;
    private List<ClientRewardLogResponse> rewardLogs;
}
