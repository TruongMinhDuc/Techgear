package com.techgear.techgear_be.repositories.reward;

import com.techgear.techgear_be.models.reward.RewardStrategy;
import com.techgear.techgear_be.models.reward.RewardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RewardStrategyRepository extends JpaRepository<RewardStrategy, Long>, JpaSpecificationExecutor<RewardStrategy> {

    Optional<RewardStrategy> findByCodeAndStatus(RewardType code, Integer status);

}
