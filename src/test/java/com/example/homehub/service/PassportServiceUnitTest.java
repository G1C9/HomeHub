package com.example.homehub.service;

import com.example.homehub.entity.Owner;
import com.example.homehub.entity.Passport;
import com.example.homehub.repository.PassportRepository;
import com.example.homehub.service.impl.PassportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PassportServiceUnitTest {

    @Mock
    private PassportRepository passportRepository;

    @InjectMocks
    private PassportServiceImpl passportService;

    @Test
    public void getByOwnerIdTest() {
        Owner owner = Owner.builder()
                .id(UUID.randomUUID())
                .build();

        Passport passport = Passport.builder()
                .id(UUID.randomUUID())
                .owner(owner)
                .build();

        when(passportRepository.getByOwnerId(passport.getOwner().getId())).thenReturn(passport);

        Passport result = passportService.getByOwnerId(passport.getOwner().getId());

        assertNotNull(result);
        assertEquals(passport.getOwner().getId(), result.getOwner().getId());

        verify(passportRepository, times(1)).getByOwnerId(passport.getOwner().getId());
    }

    @Test
    public void getByOwnerFirstNameTest() {
        String firstLetter = "M";
        Owner owner = Owner.builder()
                .id(UUID.randomUUID())
                .firstName("Misha")
                .build();

        Passport passport = Passport.builder()
                .id(UUID.randomUUID())
                .owner(owner)
                .build();

        when(passportRepository.getPassportByOwnerFirstLetter(firstLetter)).thenReturn(passport);

        Passport result = passportService.getPassportByOwnerFirstName(firstLetter);

        assertNotNull(result);
        assertEquals(passport.getId(), result.getId());
    }

    @Test
    public void createTest() {
        Passport passport = Passport.builder()
                .id(UUID.randomUUID())
                .build();

        Passport savedPassport = new Passport();

        when(passportRepository.save(any(Passport.class))).thenReturn(savedPassport);

        Passport result = passportService.create(passport);

        assertNotNull(result);
        assertEquals(savedPassport.getId(), result.getId());

        verify(passportRepository, times(1)).save(any(Passport.class));
    }

    @Test
    public void deleteTest() {
        Passport passport = Passport.builder()
                .id(UUID.randomUUID())
                .build();

        passportService.delete(passport.getId());

        verify(passportRepository, times(1)).deleteById(passport.getId());
    }

}
