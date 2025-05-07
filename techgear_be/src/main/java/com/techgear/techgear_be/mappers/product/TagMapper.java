package com.techgear.techgear_be.mappers.product;

import com.techgear.techgear_be.dtos.product.TagRequest;
import com.techgear.techgear_be.dtos.product.TagResponse;
import com.techgear.techgear_be.models.product.Tag;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper extends GenericMapper<Tag, TagRequest, TagResponse> {
}
