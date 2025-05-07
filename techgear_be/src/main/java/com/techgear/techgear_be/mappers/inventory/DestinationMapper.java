package com.techgear.techgear_be.mappers.inventory;

import com.techgear.techgear_be.dtos.inventory.DestinationRequest;
import com.techgear.techgear_be.dtos.inventory.DestinationResponse;
import com.techgear.techgear_be.models.inventory.Destination;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.mappers.address.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AddressMapper.class)
public interface DestinationMapper extends GenericMapper<Destination, DestinationRequest, DestinationResponse> {}
