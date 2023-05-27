package com.learning.employeeservice.dto;

import lombok.Data;


@Data
public class EmployeeRequest {
    private String id;
    private String name;
    private String designation;
    private String companyName;
}
