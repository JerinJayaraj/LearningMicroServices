package com.learning.employeeservice.service;

import com.learning.employeeservice.dto.AddressResponse;
import com.learning.employeeservice.dto.EmployeeRequest;
import com.learning.employeeservice.dto.EmployeeResponse;
import com.learning.employeeservice.model.Employee;
import com.learning.employeeservice.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${addressService.baseUrl}")
    private String addressServiceBaseUrl;

    public EmployeeService() {
    }

    public boolean createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .id(employeeRequest.getId())
                .name(employeeRequest.getName())
                .designation(employeeRequest.getDesignation())
                .companyName(employeeRequest.getCompanyName())
                .build();
        employeeRepository.save(employee);
        return true;
    }

    public Optional<EmployeeResponse> getEmployeeById(String employeeId) {
        Optional<Employee> employeeObject = employeeRepository.findById(employeeId);
        if (employeeObject.isPresent()) {
            Employee employee = employeeObject.get();
            EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
            AddressResponse addressResponse = restTemplate.getForObject(addressServiceBaseUrl+"address/{id}", AddressResponse.class, employeeId);
            employeeResponse.setAddressResponse(addressResponse);
            return Optional.of(employeeResponse);
        }
        return Optional.empty();
    }
}
