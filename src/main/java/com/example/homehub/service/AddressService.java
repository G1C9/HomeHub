package com.example.homehub.service;

import com.example.homehub.entity.Address;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface AddressService {

    Address getOne(UUID id);

    List<Address> getAll();

    Address create(Address address);

    Address update(Address address, UUID id);

    void delete(UUID id);

    void deleteAll();

}
