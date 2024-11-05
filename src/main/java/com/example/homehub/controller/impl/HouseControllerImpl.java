package com.example.homehub.controller.impl;

import com.example.homehub.controller.HouseController;
import com.example.homehub.dto.rq.HouseRq;
import com.example.homehub.dto.rs.HouseRs;
import com.example.homehub.mapper.controller.HouseMapper;
import com.example.homehub.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HouseControllerImpl implements HouseController {

    private final HouseService houseService;

    private final HouseMapper houseMapper;

    @Override
    public HouseRs getOne(UUID id) {
        return houseMapper.map(houseService.getOne(id));
    }

    @Override
    public List<HouseRs> getAll() {
        return houseMapper.map(houseService.getAll());
    }

    @Override
    public HouseRs create(HouseRq houseRq) {
        return houseMapper.map(houseService.create(houseMapper.map(houseRq)));
    }

    @Override
    public HouseRs update(HouseRq houseRq, UUID id) {
        return houseMapper.map(houseService.update(houseMapper.map(houseRq), id));
    }

    @Override
    public void delete(UUID id) {
        houseService.delete(id);
    }

    @Override
    public void deleteAll() {
        houseService.deleteAll();
    }

}
