package com.example.homehub.controller.impl;

import com.example.homehub.controller.OwnerController;
import com.example.homehub.dto.rq.OwnerRq;
import com.example.homehub.dto.rs.OwnerRs;
import com.example.homehub.mapper.controller.OwnerMapper;
import com.example.homehub.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OwnerControllerImpl implements OwnerController {

    private final OwnerService ownerService;

    private final OwnerMapper ownerMapper;

    @Override
    public OwnerRs getOne(UUID id) {
        return ownerMapper.map(ownerService.getOne(id));
    }

    @Override
    public OwnerRs getByStreet(String street) {
        return ownerMapper.map(ownerService.getByStreet(street));
    }

    @Override
    public List<OwnerRs> findMaleWithCarAndHouse() {
        return ownerMapper.map(ownerService.findMaleWithCarAndHouse());
    }

    @Override
    public List<OwnerRs> getAll() {
        return ownerMapper.map(ownerService.getAll());
    }

    @Override
    public OwnerRs create(OwnerRq ownerRq) {
        return ownerMapper.map(ownerService.create(ownerMapper.map(ownerRq)));
    }

    @Override
    public OwnerRs update(OwnerRq ownerRq, UUID id) {
        return ownerMapper.map(ownerService.update(ownerMapper.map(ownerRq), id));
    }

    @Override
    public void delete(UUID id) {
        ownerService.delete(id);
    }

    @Override
    public void deleteAll() {
        ownerService.deleteAll();
    }

}
