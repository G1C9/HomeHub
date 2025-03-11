package com.example.homehub.service;

import com.example.homehub.entity.Address;
import com.example.homehub.entity.House;
import com.example.homehub.exception.IdNotFoundException;
import com.example.homehub.repository.AddressRepository;
import com.example.homehub.service.impl.AddressServiceImpl;
import com.example.homehub.service.impl.HouseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
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
public class AddressServiceUnitTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private HouseServiceImpl houseService;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    public void testGetOneSuccess() {
        Address address = Address.builder()
                .id(UUID.randomUUID())
                .build();

        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));

        Address result = addressService.getOne(address.getId());

        assertEquals(address.getId(), result.getId());

        verify(addressRepository, times(1)).findById(address.getId());

    }

    @Test
    public void testGetOneNotFound() {
        UUID addressId = UUID.randomUUID();

        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> addressService.getOne(addressId));

        verify(addressRepository, times(1)).findById(addressId);
    }

    @Test
    public void testGetAll() {
        List<Address> addresses = List.of(
                Address.builder().id(UUID.randomUUID()).build(),
                Address.builder().id(UUID.randomUUID()).build()
        );

        when(addressRepository.findAll()).thenReturn(addresses);

        List<Address> result = addressService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(addressRepository, times(1)).findAll();
    }

    @Test
    public void testCreate() {
        Address address = new Address();

        Address savedAddress = Address.builder()
                .id(UUID.randomUUID())
                .build();

        when(addressRepository.save(any(Address.class))).thenReturn(savedAddress);

        Address result = addressService.create(address);

        assertNotNull(result);
        assertEquals(savedAddress.getId(), result.getId());

        verify(addressRepository, times(1)).save(any(Address.class));

    }

    @Test
    public void testUpdateSuccess() {
        Address address = Address.builder()
                .id(UUID.randomUUID())
                .country("USA")
                .city("NY")
                .street("5th Avenue")
                .build();

        Address newAddress = new Address();

        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(newAddress);

        Address result = addressService.update(newAddress, address.getId());

        assertNotNull(result);
        assertEquals(newAddress.getId(), result.getId());
        assertEquals(newAddress.getCountry(), result.getCountry());
        assertEquals(newAddress.getCity(), result.getCity());
        assertEquals(newAddress.getStreet(), result.getStreet());

        verify(addressRepository, times(1)).findById(address.getId());
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    public void testUpdateNotFound() {
        Address newAddress = Address.builder()
                .id(UUID.randomUUID())
                .build();

        when(addressRepository.findById(newAddress.getId())).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> addressService.update(newAddress, newAddress.getId()));

        verify(addressRepository, times(1)).findById(newAddress.getId());
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    public void testDeleteSuccess() {
        Address address = Address.builder()
                .id(UUID.randomUUID())
                .build();

        List<House> houses = List.of(House.builder()
                .id(UUID.randomUUID())
                .address(address)
                .build());

        when(houseService.findAllByAddressId(address.getId())).thenReturn(houses);

        addressService.delete(address.getId());

        verify(houseService, times(1)).findAllByAddressId(address.getId());
        verify(houseService, times(1)).deleteAllByAddressId(address.getId());
        verify(addressRepository, times(1)).deleteById(address.getId());
    }

    @Test
    public void testDeleteNoHouses() {
        UUID addressId = UUID.randomUUID();

        when(houseService.findAllByAddressId(addressId)).thenReturn(Collections.emptyList());

        addressService.delete(addressId);

        verify(houseService, times(1)).findAllByAddressId(addressId);
        verify(houseService, never()).deleteAllByAddressId(any(UUID.class));
        verify(addressRepository, times(1)).deleteById(addressId);
    }

    @Test
    public void testDeleteAll() {
        addressService.deleteAll();

        verify(addressRepository, times(1)).deleteAll();
    }

}
