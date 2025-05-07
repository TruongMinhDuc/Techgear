package com.techgear.techgear_be.mappers.employee;

import com.techgear.techgear_be.dtos.employee.EmployeeRequest;
import com.techgear.techgear_be.dtos.employee.EmployeeResponse;
import com.techgear.techgear_be.models.employee.Employee;
import com.techgear.techgear_be.mappers.GenericMapper;
import com.techgear.techgear_be.mappers.authentication.UserMapper;
import com.techgear.techgear_be.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MapperUtils.class, UserMapper.class})
public interface EmployeeMapper extends GenericMapper<Employee, EmployeeRequest, EmployeeResponse> {

    @Override
    @Mapping(source = "officeId", target = "office")
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "jobTypeId", target = "jobType")
    @Mapping(source = "jobLevelId", target = "jobLevel")
    @Mapping(source = "jobTitleId", target = "jobTitle")
    Employee requestToEntity(EmployeeRequest request);

    @Override
    @Mapping(source = "officeId", target = "office")
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "jobTypeId", target = "jobType")
    @Mapping(source = "jobLevelId", target = "jobLevel")
    @Mapping(source = "jobTitleId", target = "jobTitle")
    Employee partialUpdate(@MappingTarget Employee entity, EmployeeRequest request);

}
