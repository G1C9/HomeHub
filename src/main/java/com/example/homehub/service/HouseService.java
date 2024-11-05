package com.example.homehub.service;

import com.example.homehub.entity.House;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface HouseService {

    House getOne(UUID id);

    List<House> getAll();

    List<House> findAllByAddressId(UUID addressId);

    House create(House house);

    House update(House house, UUID id);

    void delete(UUID id);

    void deleteAll();

    void deleteAllByAddressId(UUID addressId);

}
