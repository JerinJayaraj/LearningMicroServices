package com.learning.employeeservice.controller;

import com.learning.employeeservice.dto.EmployeeRequest;
import com.learning.employeeservice.dto.EmployeeResponse;
import com.learning.employeeservice.model.Employee;
import com.learning.employeeservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<Object> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        boolean created = employeeService.createEmployee(employeeRequest);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return null;
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable String id) {
        Optional<EmployeeResponse> employeeResponseObject = employeeService.getEmployeeById(id);
        if (employeeResponseObject.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(employeeResponseObject.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
