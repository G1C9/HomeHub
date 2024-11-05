package com.example.homehub.repository;

import com.example.homehub.entity.Passport;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PassportRepository {

    Passport getByOwnerId(UUID ownerId);

    Passport getPassportByOwnerFirstLetter(@Param("firstLetter") String s);

    Passport save(Passport passport);

    void deleteById(UUID id);

}
