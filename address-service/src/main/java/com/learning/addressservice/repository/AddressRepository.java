package com.learning.addressservice.repository;

import com.learning.addressservice.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {
    @Query("{'employeeId' : ?0}")
    Optional<Address> findAddressByEmployeeId(String employeeId);
}
