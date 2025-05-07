package com.techgear.techgear_be.mappers.employee;

import com.techgear.techgear_be.dtos.employee.JobTypeRequest;
import com.techgear.techgear_be.dtos.employee.JobTypeResponse;
import com.techgear.techgear_be.models.employee.JobType;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobTypeMapper extends GenericMapper<JobType, JobTypeRequest, JobTypeResponse> {
}
