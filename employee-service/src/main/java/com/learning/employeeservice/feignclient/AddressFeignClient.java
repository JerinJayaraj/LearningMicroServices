package com.learning.employeeservice.feignclient;

import com.learning.employeeservice.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "address-service", url = "http://localhost:8081/address-app/api")
public interface AddressFeignClient {

    @GetMapping("/address/{empId}")
    public ResponseEntity<AddressResponse> getAddressByEmployeeId(@PathVariable String empId);
}
