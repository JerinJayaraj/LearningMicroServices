package com.learning.addressservice.service;

import com.learning.addressservice.dto.AddressRequest;
import com.learning.addressservice.dto.AddressResponse;
import com.learning.addressservice.model.Address;
import com.learning.addressservice.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    public boolean createAddress(AddressRequest addressRequest) {
        Address address = Address.builder()
                .id(addressRequest.getId())
                .houseName(addressRequest.getHouseName())
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .country(addressRequest.getCountry())
                .zipCode(addressRequest.getZipCode())
                .employeeId(addressRequest.getEmployeeId())
                .build();
        addressRepository.save(address);
        return true;
    }

    public Optional<AddressResponse> getAddressByEmployeeId(String employeeId) {
        Optional<Address> addressOfEmployee = addressRepository.findAddressByEmployeeId(employeeId);
        if (addressOfEmployee.isPresent()) {
            Address address = addressOfEmployee.get();
            AddressResponse addressResponse = modelMapper.map(address, AddressResponse.class);
            return Optional.of(addressResponse);
        }
        return Optional.empty();
    }
}
