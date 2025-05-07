package com.techgear.techgear_be.mappers.reward;

import com.techgear.techgear_be.dtos.reward.RewardStrategyRequest;
import com.techgear.techgear_be.dtos.reward.RewardStrategyResponse;
import com.techgear.techgear_be.models.reward.RewardStrategy;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RewardStrategyMapper extends GenericMapper<RewardStrategy, RewardStrategyRequest, RewardStrategyResponse> {

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RewardStrategy partialUpdate(@MappingTarget RewardStrategy entity, RewardStrategyRequest request);

}
