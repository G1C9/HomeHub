package com.example.homehub.service;

import com.example.homehub.entity.House;
import com.example.homehub.entity.HouseOwners;
import com.example.homehub.entity.Owner;
import com.example.homehub.exception.IdNotFoundException;
import com.example.homehub.repository.HouseOwnersRepository;
import com.example.homehub.service.impl.HouseOwnersServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HouseOwnersServiceUnitTest {

    @Mock
    HouseOwnersRepository houseOwnersRepository;

    @InjectMocks
    HouseOwnersServiceImpl houseOwnersService;

    @Test
    public void testGetOneSuccess() {
        HouseOwners houseOwners = HouseOwners.builder()
                .id(UUID.randomUUID())
                .build();

        when(houseOwnersRepository.findById(houseOwners.getId())).thenReturn(Optional.of(houseOwners));

        HouseOwners result = houseOwnersService.getOne(houseOwners.getId());

        assertNotNull(result);
        assertEquals(houseOwners.getId(), result.getId());

        verify(houseOwnersRepository, times(1)).findById(houseOwners.getId());
    }

    @Test
    public void testGetOneNotFound() {
        UUID houseOwnersId = UUID.randomUUID();

        when(houseOwnersRepository.findById(houseOwnersId)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> houseOwnersService.getOne(houseOwnersId));

        verify(houseOwnersRepository, times(1)).findById(houseOwnersId);
    }

    @Test
    public void testFindByHouseId() {
        HouseOwners houseOwners = HouseOwners.builder()
                .id(UUID.randomUUID())
                .house(House.builder().id(UUID.randomUUID()).build())
                .build();

        when(houseOwnersRepository.findHouseOwnersByHouseId(houseOwners.getHouse().getId())).thenReturn(Optional.of(houseOwners));

        Optional<HouseOwners> result = houseOwnersService.findHouseOwnersByHouseId(houseOwners.getHouse().getId());

        assertTrue(result.isPresent());
        assertEquals(houseOwners.getHouse().getId(), result.get().getHouse().getId());

        verify(houseOwnersRepository, times(1)).findHouseOwnersByHouseId(houseOwners.getHouse().getId());
    }

    @Test
    public void testFindByOwnerId() {
        HouseOwners houseOwners = HouseOwners.builder()
                .id(UUID.randomUUID())
                .owner(Owner.builder().id(UUID.randomUUID()).build())
                .build();

        when(houseOwnersRepository.findHouseOwnersByOwnerId(houseOwners.getOwner().getId())).thenReturn(Optional.of(houseOwners));

        Optional<HouseOwners> result = houseOwnersService.findHouseOwnersByOwnerId(houseOwners.getOwner().getId());

        assertTrue(result.isPresent());
        assertEquals(houseOwners.getOwner().getId(), result.get().getOwner().getId());

        verify(houseOwnersRepository, times(1)).findHouseOwnersByOwnerId(houseOwners.getOwner().getId());
    }

    @Test
    public void testGetAll() {
        List<HouseOwners> houseOwners = List.of(
                HouseOwners.builder().id(UUID.randomUUID()).build(),
                HouseOwners.builder().id(UUID.randomUUID()).build()
        );

        when(houseOwnersRepository.findAll()).thenReturn(houseOwners);

        List<HouseOwners> result = houseOwnersService.getAll();

        assertNotNull(houseOwners);
        assertEquals(2, result.size());

        verify(houseOwnersRepository, times(1)).findAll();
    }

    @Test
    public void testCreate() {
        HouseOwners houseOwners = new HouseOwners();

        HouseOwners savedHouseOwners = HouseOwners.builder()
                .id(UUID.randomUUID())
                .build();

        when(houseOwnersRepository.save(any(HouseOwners.class))).thenReturn(savedHouseOwners);

        HouseOwners result = houseOwnersService.create(houseOwners);

        assertNotNull(result);
        assertEquals(savedHouseOwners.getId(), result.getId());

        verify(houseOwnersRepository, times(1)).save(any(HouseOwners.class));
    }

    @Test
    public void testUpdateSuccess() {
        HouseOwners houseOwners = HouseOwners.builder()
                .id(UUID.randomUUID())
                .house(new House())
                .owner(new Owner())
                .build();

        HouseOwners newHouseOwners = new HouseOwners();

        when(houseOwnersRepository.findById(houseOwners.getId())).thenReturn(Optional.of(houseOwners));
        when(houseOwnersRepository.save(any(HouseOwners.class))).thenReturn(newHouseOwners);

        HouseOwners result = houseOwnersService.update(newHouseOwners, houseOwners.getId());

        assertNotNull(result);
        assertEquals(newHouseOwners.getId(), result.getId());
        assertEquals(newHouseOwners.getHouse(), result.getHouse());
        assertEquals(newHouseOwners.getOwner(), result.getOwner());

        verify(houseOwnersRepository, times(1)).findById(houseOwners.getId());
        verify(houseOwnersRepository, times(1)).save(any(HouseOwners.class));
    }

    @Test
    public void testUpdateNotFound() {
        HouseOwners houseOwners = HouseOwners.builder()
                .id(UUID.randomUUID())
                .build();

        when(houseOwnersRepository.findById(houseOwners.getId())).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> houseOwnersService.update(houseOwners, houseOwners.getId()));

        verify(houseOwnersRepository, times(1)).findById(houseOwners.getId());
        verify(houseOwnersRepository, never()).save(any(HouseOwners.class));
    }

    @Test
    public void testDelete() {
        HouseOwners houseOwners = HouseOwners.builder()
                .id(UUID.randomUUID())
                .build();

        houseOwnersService.delete(houseOwners.getId());

        verify(houseOwnersRepository, times(1)).deleteById(houseOwners.getId());
    }

    @Test
    public void testDeleteByHouseId() {
        House house = House.builder()
                .id(UUID.randomUUID())
                .build();

        HouseOwners houseOwners = HouseOwners.builder()
                .id(UUID.randomUUID())
                .house(house)
                .build();

        houseOwnersService.deleteByHouseId(houseOwners.getHouse().getId());

        verify(houseOwnersRepository, times(1)).deleteByHouseId(houseOwners.getHouse().getId());
    }

    @Test
    public void testDeleteByOwnerId() {
        Owner owner = Owner.builder()
                .id(UUID.randomUUID())
                .build();

        HouseOwners houseOwners = HouseOwners.builder()
                .id(UUID.randomUUID())
                .owner(owner)
                .build();

        houseOwnersService.deleteByOwnerId(houseOwners.getOwner().getId());

        verify(houseOwnersRepository, times(1)).deleteByOwnerId(houseOwners.getOwner().getId());
    }

    @Test
    public void testDeleteAll() {
        houseOwnersService.deleteAll();

        verify(houseOwnersRepository, times(1)).deleteAll();
    }

}
