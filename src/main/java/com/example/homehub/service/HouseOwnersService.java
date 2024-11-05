package com.example.homehub.service;

import com.example.homehub.entity.HouseOwners;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface HouseOwnersService {

    HouseOwners getOne(UUID id);

    Optional<HouseOwners> findHouseOwnersByHouseId(UUID houseId);

    Optional<HouseOwners> findHouseOwnersByOwnerId(UUID ownerId);

    List<HouseOwners> getAll();

    HouseOwners create(HouseOwners houseOwners);

    HouseOwners update(HouseOwners houseOwners, UUID id);

    void delete(UUID id);

    void deleteByHouseId(UUID houseId);

    void deleteByOwnerId(UUID ownerId);

    void deleteAll();

}
