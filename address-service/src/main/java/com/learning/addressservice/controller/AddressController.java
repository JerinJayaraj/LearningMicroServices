package com.learning.addressservice.controller;

import com.learning.addressservice.dto.AddressRequest;
import com.learning.addressservice.dto.AddressResponse;
import com.learning.addressservice.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/address")
    public ResponseEntity createAddress(@RequestBody AddressRequest addressRequest) {
        boolean isAddressCreated = addressService.createAddress(addressRequest);
        if (isAddressCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/address/{empId}")
    public ResponseEntity<AddressResponse> getEmployeeById(@PathVariable String empId) {
        Optional<AddressResponse> addressResponse = addressService.getAddressByEmployeeId(empId);
        if (addressResponse.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(addressResponse.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
