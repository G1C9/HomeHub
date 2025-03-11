package com.example.homehub.repository;

import com.example.homehub.entity.Owner;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerRepository {

    Optional<Owner> findById(UUID id);

    List<Owner> findByStreet(@Param("street") String street);

    List<Owner> findMaleWithCarAndHouse();

    List<Owner> findAll();

    Owner save(Owner owner);

    void deleteById(UUID id);

    void deleteAll();

}
