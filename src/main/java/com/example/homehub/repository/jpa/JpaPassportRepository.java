package com.example.homehub.repository.jpa;

import com.example.homehub.entity.Passport;
import com.example.homehub.repository.PassportRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

@Profile("jpa")
public interface JpaPassportRepository extends JpaRepository<Passport, UUID>, PassportRepository {

    Passport getByOwnerId(UUID id);

    @Query("SELECT p from Passport p where p.owner.firstName LIKE :firstLetter")
    Passport getPassportByOwnerFirstLetter(@Param("firstLetter") String firstLetter);

}
