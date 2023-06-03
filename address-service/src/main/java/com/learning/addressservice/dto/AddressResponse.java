package com.learning.addressservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressResponse {
    private String id;
    private String houseName;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
