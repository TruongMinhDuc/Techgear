package com.techgear.techgear_be.mappers.inventory;

import com.techgear.techgear_be.dtos.inventory.DocketReasonRequest;
import com.techgear.techgear_be.dtos.inventory.DocketReasonResponse;
import com.techgear.techgear_be.models.inventory.DocketReason;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocketReasonMapper extends GenericMapper<DocketReason, DocketReasonRequest, DocketReasonResponse> {}
