package com.example.homehub.repository;

import com.example.homehub.entity.House;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HouseRepository {

    Optional<House> findById(UUID id);

    List<House> findAll();

    List<House> findAllByAddressId(UUID addressId);

    House save(House house);

    void deleteById(UUID id);

    void deleteAll();

    void deleteAllByAddressId(UUID addressId);

}
