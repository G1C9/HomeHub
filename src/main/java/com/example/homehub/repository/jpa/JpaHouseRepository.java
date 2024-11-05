package com.example.homehub.repository.jpa;

import com.example.homehub.entity.House;
import com.example.homehub.repository.HouseRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

@Profile("jpa")
public interface JpaHouseRepository extends JpaRepository<House, UUID>, HouseRepository {

    List<House> findAllByAddressId(UUID addressId);

    void deleteAllByAddressId(UUID addressId);

}
