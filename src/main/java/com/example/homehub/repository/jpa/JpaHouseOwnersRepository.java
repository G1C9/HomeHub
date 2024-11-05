package com.example.homehub.repository.jpa;

import com.example.homehub.entity.HouseOwners;
import com.example.homehub.repository.HouseOwnersRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

@Profile("jpa")
public interface JpaHouseOwnersRepository extends JpaRepository<HouseOwners, UUID>, HouseOwnersRepository {

    Optional<HouseOwners> findHouseOwnersByHouseId(UUID houseId);

    Optional<HouseOwners> findHouseOwnersByOwnerId(UUID ownerId);

    void deleteByHouseId(UUID id);

    void deleteByOwnerId(UUID id);

}
