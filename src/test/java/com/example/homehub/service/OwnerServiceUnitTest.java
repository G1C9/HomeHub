package com.example.homehub.service;

import com.example.homehub.entity.Car;
import com.example.homehub.entity.HouseOwners;
import com.example.homehub.entity.Owner;
import com.example.homehub.entity.Passport;
import com.example.homehub.exception.IdNotFoundException;
import com.example.homehub.repository.OwnerRepository;
import com.example.homehub.service.impl.CarServiceImpl;
import com.example.homehub.service.impl.HouseOwnersServiceImpl;
import com.example.homehub.service.impl.OwnerServiceImpl;
import com.example.homehub.service.impl.PassportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceUnitTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private CarServiceImpl carService;

    @Mock
    private PassportServiceImpl passportService;

    @Mock
    private HouseOwnersServiceImpl houseOwnersService;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Test
    public void testGetOneSuccess() {
        Owner owner = Owner.builder()
                .id(UUID.randomUUID())
                .build();

        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));

        Owner result = ownerService.getOne(owner.getId());

        assertNotNull(result);
        assertEquals(owner.getId(), result.getId());

        verify(ownerRepository, times(1)).findById(owner.getId());
    }

    @Test
    public void testGetOneNotFound() {
        Owner owner = Owner.builder()
                .id(UUID.randomUUID())
                .build();

        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> ownerService.getOne(owner.getId()));

        verify(ownerRepository, times(1)).findById(owner.getId());
    }

    @Test
    public void testGetByStreet() {
        Owner firstOwner = Owner.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .gender("Male")
                .build();

        Owner secondOwner = Owner.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .gender("Male")
                .build();

        Owner thirdOwner = Owner.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .gender("Male")
                .build();

        List<Owner> expectedOwners = List.of(firstOwner, secondOwner, thirdOwner);

        when(ownerRepository.findByStreet("Main Street")).thenReturn(expectedOwners);

        List<Owner> result = ownerService.getByStreet("Main Street");

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(firstOwner.getFirstName(), result.get(0).getFirstName());
        assertEquals(secondOwner.getLastName(), result.get(1).getLastName());
        assertEquals(thirdOwner.getGender(), result.get(2).getGender());

        verify(ownerRepository, times(1)).findByStreet("Main Street");
    }

    @Test
    public void testFindMaleWithCarAndHouse() {
        Owner firstOwner = Owner.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .gender("Male")
                .build();

        Owner secondOwner = Owner.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .gender("Male")
                .build();

        Owner thirdOwner = Owner.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .gender("Male")
                .build();

        List<Owner> expectedOwners = List.of(firstOwner, secondOwner, thirdOwner);

        when(ownerRepository.findMaleWithCarAndHouse()).thenReturn(expectedOwners);

        List<Owner> result = ownerService.findMaleWithCarAndHouse();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(firstOwner.getFirstName(), result.get(0).getFirstName());
        assertEquals(secondOwner.getLastName(), result.get(1).getLastName());
        assertEquals(thirdOwner.getGender(), result.get(2).getGender());

        verify(ownerRepository, times(1)).findMaleWithCarAndHouse();
    }

    @Test
    public void testGetAll() {
        List<Owner> owners = List.of(
                Owner.builder().id(UUID.randomUUID()).build(),
                Owner.builder().id(UUID.randomUUID()).build()
        );

        when(ownerRepository.findAll()).thenReturn(owners);

        List<Owner> result = ownerService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    public void testCreate() {
        Owner owner = new Owner();

        Passport passport = Passport.builder()
                .id(UUID.randomUUID())
                .build();

        Owner savedOwner = Owner.builder()
                .id(UUID.randomUUID())
                .passport(passport)
                .build();

        when(passportService.create(any(Passport.class))).thenReturn(passport);
        when(ownerRepository.save(any(Owner.class))).thenReturn(savedOwner);

        Owner result = ownerService.create(owner);

        assertNotNull(result);
        assertEquals(savedOwner.getId(), result.getId());
        assertEquals(savedOwner.getPassport(), result.getPassport());

        verify(passportService, times(1)).create(any(Passport.class));
        verify(ownerRepository, times(1)).save(any(Owner.class));
    }

    @Test
    public void testUpdateSuccess() {
        Owner owner = Owner.builder()
                .id(UUID.randomUUID())
                .build();

        Owner newOwner = new Owner();

        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.of(newOwner));
        when(ownerRepository.save(any(Owner.class))).thenReturn(newOwner);

        Owner result = ownerService.update(newOwner, owner.getId());

        assertNotNull(result);
        assertEquals(newOwner.getId(), result.getId());

        verify(ownerRepository, times(1)).findById(owner.getId());
        verify(ownerRepository, times(1)).save(any(Owner.class));
    }

    @Test
    public void testUpdateNotFound() {
        Owner owner = Owner.builder()
                .id(UUID.randomUUID())
                .build();

        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> ownerService.update(owner, owner.getId()));

        verify(ownerRepository, times(1)).findById(owner.getId());
        verify(ownerRepository, never()).save(any(Owner.class));
    }

    @Test
    public void testDelete() {
        Passport passport = Passport.builder()
                .id(UUID.randomUUID())
                .build();

        Owner owner = Owner.builder()
                .id(UUID.randomUUID())
                .passport(passport)
                .build();

        HouseOwners houseOwners = HouseOwners.builder()
                .id(UUID.randomUUID())
                .build();

        Car car = Car.builder()
                .id(UUID.randomUUID())
                .build();

        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(houseOwnersService.findHouseOwnersByOwnerId(owner.getId())).thenReturn(Optional.of(houseOwners));
        when(carService.findAllByOwnerId(owner.getId())).thenReturn(List.of(car));

        ownerService.delete(owner.getId());

        verify(passportService, times(1)).delete(passport.getId());
        verify(houseOwnersService, times(1)).findHouseOwnersByOwnerId(owner.getId());
        verify(houseOwnersService, times(1)).deleteByOwnerId(owner.getId());
        verify(carService, times(1)).findAllByOwnerId(owner.getId());
        verify(carService, times(1)).deleteAllByOwnerId(owner.getId());
        verify(ownerRepository, times(1)).deleteById(owner.getId());
    }

    @Test
    public void testDeleteOwnerNotFound() {
        Owner owner = Owner.builder()
                .id(UUID.randomUUID())
                .build();

        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> ownerService.delete(owner.getId()));

        verify(ownerRepository, times(1)).findById(owner.getId());
        verify(passportService, never()).delete(any());
        verify(houseOwnersService, never()).deleteByOwnerId(any());
        verify(carService, never()).deleteAllByOwnerId(any());
        verify(ownerRepository, never()).deleteById(any());
    }

    @Test
    public void testDeleteAllOwners() {
        ownerService.deleteAll();

        verify(ownerRepository, times(1)).deleteAll();
    }

}
