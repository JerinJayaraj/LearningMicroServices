package com.learning.employeeservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeResponse {
    private String id;
    private String name;
    private String designation;
    private String companyName;
}
