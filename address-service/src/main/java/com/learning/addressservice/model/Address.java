package com.learning.addressservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document
public class Address {
    @Id
    private String id;
    private String houseName;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String employeeId;
}
