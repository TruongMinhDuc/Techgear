package com.techgear.techgear_be.mappers.address;

import com.techgear.techgear_be.dtos.address.province.ProvinceRequest;
import com.techgear.techgear_be.dtos.address.province.ProvinceResponse;
import com.techgear.techgear_be.models.address.Province;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProvinceMapper extends GenericMapper<Province, ProvinceRequest, ProvinceResponse> {
}
