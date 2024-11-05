package com.example.homehub.repository.jpa;

import com.example.homehub.entity.Owner;
import com.example.homehub.repository.OwnerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

@Profile("jpa")
public interface JpaOwnerRepository extends JpaRepository<Owner, UUID>, OwnerRepository {

    @Query("SELECT o FROM Owner o " +
            "JOIN o.houseOwners ho " +
            "JOIN ho.house h " +
            "JOIN h.address a " +
            "WHERE a.street = :street")
    Owner getByStreet(@Param("street") String street);

    @Query(value = "SELECT DISTINCT o.* FROM Owner o " +
            "JOIN Car c ON o.id = c.owner_id " +
            "JOIN HouseOwners ho ON o.id = ho.owner_id " +
            "JOIN House h ON ho.house_id = h.id " +
            "WHERE o.gender = 'Male'", nativeQuery = true)
    List<Owner> findMaleWithCarAndHouse();

}
