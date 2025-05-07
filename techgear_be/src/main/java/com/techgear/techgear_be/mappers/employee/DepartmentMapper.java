package com.techgear.techgear_be.mappers.employee;

import com.techgear.techgear_be.dtos.employee.DepartmentRequest;
import com.techgear.techgear_be.dtos.employee.DepartmentResponse;
import com.techgear.techgear_be.models.employee.Department;
import com.techgear.techgear_be.mappers.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper extends GenericMapper<Department, DepartmentRequest, DepartmentResponse> {
}
