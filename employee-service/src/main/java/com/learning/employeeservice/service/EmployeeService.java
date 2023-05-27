package com.learning.employeeservice.service;

import com.learning.employeeservice.dto.EmployeeDto;
import com.learning.employeeservice.model.Employee;
import com.learning.employeeservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean createEmployee(EmployeeDto employeeDto) {
        Employee employee = Employee.builder()
                .id(employeeDto.getId())
                .name(employeeDto.getName())
                .designation(employeeDto.getDesignation())
                .companyName(employeeDto.getCompanyName())
                .build();
        employeeRepository.save(employee);
        return true;
    }
}
