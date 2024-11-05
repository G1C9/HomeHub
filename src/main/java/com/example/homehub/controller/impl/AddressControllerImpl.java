package com.example.homehub.controller.impl;

import com.example.homehub.controller.AddressController;
import com.example.homehub.dto.rq.AddressRq;
import com.example.homehub.dto.rs.AddressRs;
import com.example.homehub.mapper.controller.AddressMapper;
import com.example.homehub.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AddressControllerImpl implements AddressController {

    private final AddressService addressService;

    private final AddressMapper addressMapper;

    @Override
    public AddressRs getOne(UUID id) {
        return addressMapper.map(addressService.getOne(id));
    }

    @Override
    public List<AddressRs> getAll() {
        return addressMapper.map(addressService.getAll());
    }

    @Override
    public AddressRs create(AddressRq addressRq) {
        return addressMapper.map(addressService.create(addressMapper.map(addressRq)));
    }

    @Override
    public AddressRs update(AddressRq addressRq, UUID id) {
        return addressMapper.map(addressService.update(addressMapper.map(addressRq), id));
    }

    @Override
    public void delete(UUID id) {
        addressService.delete(id);
    }

    @Override
    public void deleteAll() {
        addressService.deleteAll();
    }
}
