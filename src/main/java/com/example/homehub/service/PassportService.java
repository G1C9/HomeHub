package com.example.homehub.service;

import com.example.homehub.entity.Passport;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface PassportService {

    Passport getByOwnerId(UUID id);

    Passport getPassportByOwnerFirstName(@Param("firstLetter") String firstLetter);

    Passport create(Passport passport);

    void delete(UUID id);

}
