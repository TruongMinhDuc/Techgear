package com.techgear.techgear_be.mappers.address;

import com.techgear.techgear_be.dtos.address.district.DistrictRequest;
import com.techgear.techgear_be.dtos.address.district.DistrictResponse;
import com.techgear.techgear_be.models.address.District;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MapperUtils.class)
public interface DistrictMapper extends GenericMapper<District, DistrictRequest, DistrictResponse> {

    @Override
    @Mapping(source = "provinceId", target = "province")
    District requestToEntity(DistrictRequest request);

    @Override
    @Mapping(source = "provinceId", target = "province")
    District partialUpdate(@MappingTarget District entity, DistrictRequest request);

}
