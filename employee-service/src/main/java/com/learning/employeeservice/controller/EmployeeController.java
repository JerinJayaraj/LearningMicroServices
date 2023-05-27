package com.learning.employeeservice.controller;

import com.learning.employeeservice.dto.EmployeeDto;
import com.learning.employeeservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public ResponseEntity<Object> createEmployee(@RequestBody EmployeeDto employeeDto) {
        boolean created = employeeService.createEmployee(employeeDto);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return null;
    }
}
