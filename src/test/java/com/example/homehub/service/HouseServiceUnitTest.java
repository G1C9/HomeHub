package com.example.homehub.service;

import com.example.homehub.entity.Address;
import com.example.homehub.entity.House;
import com.example.homehub.entity.HouseOwners;
import com.example.homehub.exception.IdNotFoundException;
import com.example.homehub.repository.HouseRepository;
import com.example.homehub.service.impl.HouseOwnersServiceImpl;
import com.example.homehub.service.impl.HouseServiceImpl;
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
public class HouseServiceUnitTest {

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private HouseOwnersServiceImpl houseOwnersService;

    @InjectMocks
    private HouseServiceImpl houseService;

    @Test
    public void testGetOneSuccess() {
        House house = House.builder()
                .id(UUID.randomUUID())
                .build();

        when(houseRepository.findById(house.getId())).thenReturn(Optional.of(house));

        House result = houseService.getOne(house.getId());

        assertNotNull(result);
        assertEquals(house.getId(), result.getId());

        verify(houseRepository, times(1)).findById(house.getId());
    }

    @Test
    public void testGetOneNotFound() {
        UUID houseId = UUID.randomUUID();

        when(houseRepository.findById(houseId)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> houseService.getOne(houseId));

        verify(houseRepository, times(1)).findById(houseId);
    }

    @Test
    public void testGetAll() {
        List<House> houses = List.of(
                House.builder().id(UUID.randomUUID()).build(),
                House.builder().id(UUID.randomUUID()).build()
        );

        when(houseRepository.findAll()).thenReturn(houses);

        List<House> result = houseService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(houseRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllByAddressId() {
        Address address = Address.builder()
                .id(UUID.randomUUID())
                .build();

        List<House> houses = List.of(
                House.builder().id(UUID.randomUUID()).address(address).build(),
                House.builder().id(UUID.randomUUID()).address(address).build()
        );

        when(houseRepository.findAllByAddressId(address.getId())).thenReturn(houses);

        List<House> result = houseService.findAllByAddressId(address.getId());

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(houseRepository, times(1)).findAllByAddressId(address.getId());
    }

    @Test
    public void testCreate() {
        House house = new House();

        House savedHouse = House.builder()
                .id(UUID.randomUUID())
                .build();

        when(houseRepository.save(any(House.class))).thenReturn(savedHouse);

        House result = houseService.create(house);

        assertNotNull(result);
        assertEquals(savedHouse.getId(), result.getId());

        verify(houseRepository, times(1)).save(any(House.class));
    }

    @Test
    public void testUpdateSuccess() {
        House house = House.builder()
                .id(UUID.randomUUID())
                .number(99)
                .square(55)
                .address(new Address())
                .build();

        House newHouse = new House();

        when(houseRepository.findById(house.getId())).thenReturn(Optional.of(house));
        when(houseRepository.save(any(House.class))).thenReturn(newHouse);

        House result = houseService.update(newHouse, house.getId());

        assertNotNull(result);
        assertEquals(newHouse.getId(), result.getId());
        assertEquals(newHouse.getNumber(), result.getNumber());
        assertEquals(newHouse.getSquare(), result.getSquare());
        assertEquals(newHouse.getAddress(), result.getAddress());

        verify(houseRepository, times(1)).findById(house.getId());
        verify(houseRepository, times(1)).save(any(House.class));
    }

    @Test
    public void testUpdateNotFound() {
        House house = House.builder()
                .id(UUID.randomUUID())
                .build();

        when(houseRepository.findById(house.getId())).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> houseService.update(house, house.getId()));

        verify(houseRepository, times(1)).findById(house.getId());
        verify(houseRepository, never()).save(any(House.class));
    }

    @Test
    public void testDeleteSuccess() {
        House house = House.builder()
                .id(UUID.randomUUID())
                .build();

        HouseOwners houseOwner = HouseOwners.builder()
                .id(UUID.randomUUID())
                .house(house)
                .build();

        when(houseOwnersService.findHouseOwnersByHouseId(house.getId())).thenReturn(Optional.of(houseOwner));

        houseService.delete(house.getId());

        verify(houseOwnersService, times(1)).findHouseOwnersByHouseId(house.getId());
        verify(houseOwnersService, times(1)).deleteByHouseId(house.getId());
        verify(houseRepository, times(1)).deleteById(house.getId());
    }

    @Test
    public void testDeleteNoHouseOwners() {
        UUID houseId = UUID.randomUUID();

        when(houseOwnersService.findHouseOwnersByHouseId(houseId)).thenReturn(Optional.empty());

        houseService.delete(houseId);

        verify(houseOwnersService, times(1)).findHouseOwnersByHouseId(houseId);
        verify(houseOwnersService, never()).deleteByHouseId(any(UUID.class));
        verify(houseRepository,  times(1)).deleteById(houseId);
    }

    @Test
    public void testDeleteAll() {
        houseService.deleteAll();

        verify(houseRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteAllByAddressId() {
        Address address = Address.builder()
                .id(UUID.randomUUID())
                .build();

        House house = House.builder()
                .id(UUID.randomUUID())
                .address(address)
                .build();

        houseService.deleteAllByAddressId(house.getAddress().getId());

        verify(houseRepository, times(1)).deleteAllByAddressId(house.getAddress().getId());
    }

}
