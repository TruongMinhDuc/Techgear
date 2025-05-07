package com.techgear.techgear_be.mappers.inventory;

import com.techgear.techgear_be.dtos.inventory.TransferRequest;
import com.techgear.techgear_be.dtos.inventory.TransferResponse;
import com.techgear.techgear_be.models.inventory.Transfer;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = DocketMapper.class)
public interface TransferMapper extends GenericMapper<Transfer, TransferRequest, TransferResponse> {}
