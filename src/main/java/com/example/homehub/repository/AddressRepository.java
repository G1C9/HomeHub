package com.example.homehub.repository;

import com.example.homehub.entity.Address;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository {

    Optional<Address> findById(UUID id);

    List<Address> findAll();

    Address save(Address address);

    void deleteById(UUID id);

    void deleteAll();

}
