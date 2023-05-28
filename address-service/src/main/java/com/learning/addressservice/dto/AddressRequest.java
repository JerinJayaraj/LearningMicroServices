package com.learning.addressservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressRequest {
    private String id;
    private String houseName;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String employeeId;
}
