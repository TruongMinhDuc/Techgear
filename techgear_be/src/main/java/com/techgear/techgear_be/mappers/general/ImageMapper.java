package com.techgear.techgear_be.mappers.general;

import com.techgear.techgear_be.dtos.general.ImageRequest;
import com.techgear.techgear_be.dtos.general.ImageResponse;
import com.techgear.techgear_be.models.general.Image;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper extends GenericMapper<Image, ImageRequest, ImageResponse> {}
