package com.example.homehub.controller.impl;

import com.example.homehub.controller.HouseOwnersController;
import com.example.homehub.dto.rq.HouseOwnersRq;
import com.example.homehub.dto.rs.HouseOwnersRs;
import com.example.homehub.mapper.controller.HouseOwnersMapper;
import com.example.homehub.service.HouseOwnersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HouseOwnersControllerImpl implements HouseOwnersController {

    private final HouseOwnersService houseOwnersService;

    private final HouseOwnersMapper houseOwnersMapper;

    @Override
    public HouseOwnersRs getOne(UUID id) {
        return houseOwnersMapper.map(houseOwnersService.getOne(id));
    }

    @Override
    public List<HouseOwnersRs> getAll() {
        return houseOwnersMapper.map(houseOwnersService.getAll());
    }

    @Override
    public HouseOwnersRs create(HouseOwnersRq houseOwnersRq) {
        return houseOwnersMapper.map(houseOwnersService.create(houseOwnersMapper.map(houseOwnersRq)));
    }

    @Override
    public HouseOwnersRs update(HouseOwnersRq houseOwnersRq, UUID id) {
        return houseOwnersMapper.map(houseOwnersService.update(houseOwnersMapper.map(houseOwnersRq), id));
    }

    @Override
    public void delete(UUID id) {
        houseOwnersService.delete(id);
    }

    @Override
    public void deleteAll() {
        houseOwnersService.deleteAll();
    }

}
