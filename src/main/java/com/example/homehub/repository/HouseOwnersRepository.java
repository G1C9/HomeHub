package com.example.homehub.repository;

import com.example.homehub.entity.HouseOwners;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HouseOwnersRepository {

    Optional<HouseOwners> findById(UUID id);

    Optional<HouseOwners> findHouseOwnersByHouseId(UUID id);

    Optional<HouseOwners> findHouseOwnersByOwnerId(UUID id);

    List<HouseOwners> findAll();

    HouseOwners save(HouseOwners houseOwners);

    void deleteById(UUID id);

    void deleteByHouseId(UUID id);

    void deleteByOwnerId(UUID id);

    void deleteAll();

}
