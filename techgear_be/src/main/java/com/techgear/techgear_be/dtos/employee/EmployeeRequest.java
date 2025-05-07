package com.techgear.techgear_be.dtos.employee;

import com.techgear.techgear_be.dtos.authentication.user.UserRequest;
import lombok.Data;

@Data
public class EmployeeRequest {
    private UserRequest user;
    private Long officeId;
    private Long departmentId;
    private Long jobTypeId;
    private Long jobLevelId;
    private Long jobTitleId;
}
