package com.techgear.techgear_be.mappers.employee;

import com.techgear.techgear_be.dtos.employee.JobLevelRequest;
import com.techgear.techgear_be.dtos.employee.JobLevelResponse;
import com.techgear.techgear_be.models.employee.JobLevel;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobLevelMapper extends GenericMapper<JobLevel, JobLevelRequest, JobLevelResponse> {
}
