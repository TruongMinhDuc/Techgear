package com.techgear.techgear_be.mappers.employee;

import com.techgear.techgear_be.dtos.employee.JobTitleRequest;
import com.techgear.techgear_be.dtos.employee.JobTitleResponse;
import com.techgear.techgear_be.models.employee.JobTitle;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobTitleMapper extends GenericMapper<JobTitle, JobTitleRequest, JobTitleResponse> {
}
